package com.mini.project.courseservice.controllers;

import com.mini.project.courseservice.dto.LessonDto;
import com.mini.project.courseservice.dto.LessonFilterDto;
import com.mini.project.courseservice.dto.LessonRequestDto;
import com.mini.project.courseservice.service.LessonsService;
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
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
public class LessonsController {

    private final LessonsService lessonsService;

    @PostMapping
    public ResponseEntity<LessonDto> createLesson(@RequestBody @Validated LessonRequestDto lessonDto) {
        return new ResponseEntity<>(lessonsService.createLesson(lessonDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<LessonDto>> getLesson(LessonFilterDto filter, Pageable pageable) {
        return new ResponseEntity<>(lessonsService.getLesson(filter,pageable), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LessonDto> updateLesson(@PathVariable Long id, @RequestBody @Validated LessonRequestDto lessonDto) {
        return new ResponseEntity<>(lessonsService.updateLesson(id, lessonDto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonDto> getLesson(@PathVariable Long id) {
        return new ResponseEntity<>(lessonsService.getLessonById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        lessonsService.deleteLesson(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
