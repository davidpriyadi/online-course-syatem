package com.mini.project.courseservice.controllers;


import com.mini.project.courseservice.dto.ReviewsDto;
import com.mini.project.courseservice.dto.ReviewsRequestDto;
import com.mini.project.courseservice.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewsDto> create(@RequestBody @Validated ReviewsRequestDto requestDto) {
        return new ResponseEntity<>(reviewService.create(requestDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reviewService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
