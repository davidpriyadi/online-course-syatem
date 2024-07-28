package com.mini.project.userservice.service;

import com.mini.project.userservice.dto.LoginDto;
import com.mini.project.userservice.dto.TokenDto;
import com.mini.project.userservice.dto.VerifyTokenDto;
import com.mini.project.userservice.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public TokenDto login(LoginDto loginDto) {
        return usersRepository.findByEmail(loginDto.getEmail())
                .filter(user -> passwordEncoder.matches(loginDto.getPassword(), user.getPassword()))
                .map(jwtService::generateToken)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password wrong"));
    }

    public VerifyTokenDto verifyToken(String token) {
        token = jwtService.resolveToken(token);
        return jwtService.validateToken(token);
    }

    public void logout(String token) {
        token = jwtService.resolveToken(token);
        jwtService.removeToken(token);
    }
}
