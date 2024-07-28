package com.mini.project.userservice.service;

import com.mini.project.userservice.dto.TokenDto;
import com.mini.project.userservice.dto.VerifyTokenDto;
import com.mini.project.userservice.entity.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.redis.prefix}")
    private String redisPrefix;

    private final RedisTemplate<String, String> redisTemplate;

    // Membuat token JWT berdasarkan informasi pengguna
    public TokenDto generateToken(Users user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", user.getEmail());
        return createToken(claims, user.getId().toString());
    }

    // Membuat token JWT dengan konfigurasi tertentu
    private TokenDto createToken(Map<String, Object> claims, String subject) {
        var expired = LocalDateTime.now().plusSeconds(expiration);

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(expired.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, secret).compact();

        storeToken(subject, token);
        return TokenDto.builder().token(token).expired(expired).build();
    }

    private void storeToken(String id, String token) {
        String redisKey = redisPrefix + id;
        redisTemplate.opsForValue().set(redisKey, token, expiration, TimeUnit.MILLISECONDS);
    }

    public void removeToken(String token) {
        final String id = this.getUsernameFromToken(token);
        String redisKey = redisPrefix + id;
        redisTemplate.delete(redisKey);
    }

    // Mendapatkan token JWT dari header Authorization dalam request
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String resolveToken(String token) {
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }

    // Validasi token JWT terhadap informasi pengguna
    public VerifyTokenDto validateToken(String token) {
        final String id = this.getUsernameFromToken(token);
        String redisKey = redisPrefix + id;
        String storedToken = redisTemplate.opsForValue().get(redisKey);

        if (storedToken == null || !storedToken.equals(token) || isTokenExpired(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired token");
        }

        String email = extractClaim(token, claims -> claims.get("email", String.class));
        return VerifyTokenDto.builder().id(id).email(email).build();
    }

    // Mendapatkan nama pengguna dari token JWT
    public String getUsernameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Mendapatkan waktu kadaluarsa token JWT
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Mendapatkan klaim khusus dari token JWT
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Mendapatkan semua klaim dari token JWT
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    // Memeriksa apakah token JWT telah kedaluwarsa
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }


}
