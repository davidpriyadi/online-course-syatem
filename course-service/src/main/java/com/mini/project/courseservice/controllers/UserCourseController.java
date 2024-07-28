package com.mini.project.courseservice.controllers;


import com.mini.project.courseservice.dto.UserCourseDto;
import com.mini.project.courseservice.dto.UserCourseRequestDto;
import com.mini.project.courseservice.service.UserCourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/user-courses")
@RequiredArgsConstructor
public class UserCourseController {
    private final UserCourseService userCourseService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Validated UserCourseRequestDto requestDto) {
        userCourseService.create(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/premium")
    public ResponseEntity<UserCourseDto> createPremium(@RequestBody @Validated UserCourseRequestDto requestDto) {
        return new ResponseEntity<>(userCourseService.createPremium(requestDto), HttpStatus.CREATED);
    }
}
