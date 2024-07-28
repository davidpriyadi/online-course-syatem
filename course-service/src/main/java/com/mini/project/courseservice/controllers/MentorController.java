package com.mini.project.courseservice.controllers;

import com.mini.project.courseservice.dto.MentorDto;
import com.mini.project.courseservice.dto.MentorRequestDto;
import com.mini.project.courseservice.service.MentorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/mentors")
@RestController
@RequiredArgsConstructor
public class MentorController {

    private final MentorService mentorService;

    @PostMapping
    public ResponseEntity<MentorDto> createMentor(@RequestBody @Validated MentorRequestDto mentorDto) {
        return new ResponseEntity<>(mentorService.createMentor(mentorDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MentorDto> getMentor(@PathVariable Long id) {
        return new ResponseEntity<>(mentorService.getMentor(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MentorDto> updateMentor(@PathVariable Long id, @RequestBody @Validated MentorRequestDto mentorDto) {
        return new ResponseEntity<>(mentorService.updateMentor(id, mentorDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMentor(@PathVariable Long id) {
        mentorService.deleteMentor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
