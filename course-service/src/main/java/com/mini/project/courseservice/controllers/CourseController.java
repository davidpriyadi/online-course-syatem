package com.mini.project.courseservice.controllers;

import com.mini.project.courseservice.dto.CourseFilterDto;
import com.mini.project.courseservice.dto.CourseRequestDto;
import com.mini.project.courseservice.dto.CoursedDto;
import com.mini.project.courseservice.service.CourseService;
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
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<CoursedDto> createCourse(@RequestBody @Validated CourseRequestDto courseDto) {
        return new ResponseEntity<>(courseService.createCourse(courseDto), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<Page<CoursedDto>> getCourse(CourseFilterDto filterDto, Pageable pageable) {
        return new ResponseEntity<>(courseService.getCourse(filterDto, pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoursedDto> getById(@PathVariable Long id) {
        return new ResponseEntity<>(courseService.getById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CoursedDto> updateCourse(@PathVariable Long id, @RequestBody @Validated CourseRequestDto courseDto) {
        return new ResponseEntity<>(courseService.updateCourse(id, courseDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
