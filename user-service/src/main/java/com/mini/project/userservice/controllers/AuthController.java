package com.mini.project.userservice.controllers;


import com.mini.project.userservice.dto.LoginDto;
import com.mini.project.userservice.dto.RegisterDto;
import com.mini.project.userservice.dto.TokenDto;
import com.mini.project.userservice.dto.VerifyTokenDto;
import com.mini.project.userservice.service.AuthService;
import com.mini.project.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody @Validated LoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }

    @GetMapping("/verify")
    public ResponseEntity<VerifyTokenDto> verifyToken(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(authService.verifyToken(token));
    }

    @DeleteMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        authService.logout(token);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Validated RegisterDto registerDto) {
        userService.register(registerDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
