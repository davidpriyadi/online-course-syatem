package com.mini.project.userservice.controllers;

import com.mini.project.userservice.dto.RegisterDto;
import com.mini.project.userservice.dto.UsersDto;
import com.mini.project.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<UsersDto>> getUsers(Pageable pageable) {
        return new ResponseEntity<>(userService.getUsers(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsersDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

}
