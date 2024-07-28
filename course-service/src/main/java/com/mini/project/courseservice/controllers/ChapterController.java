package com.mini.project.courseservice.controllers;

import com.mini.project.courseservice.client.UserClient;
import com.mini.project.courseservice.client.dto.UsersDto;
import com.mini.project.courseservice.dto.ChapterDto;
import com.mini.project.courseservice.dto.ChapterFilter;
import com.mini.project.courseservice.dto.ChapterRequestDto;
import com.mini.project.courseservice.service.ChapterService;
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
@RequestMapping("/api/chapters")
@RequiredArgsConstructor
public class ChapterController {

    private final ChapterService chapterService;
    private final UserClient userClient;

    @PostMapping
    public ResponseEntity<ChapterDto> crateChapter(@RequestBody @Validated ChapterRequestDto chapterDto) {
        return new ResponseEntity<>(chapterService.createChapter(chapterDto), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<Page<ChapterDto>> getChapter(ChapterFilter filter, Pageable pageable) {
        return new ResponseEntity<>(chapterService.getChapter(filter,pageable), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ChapterDto> getChapter(@PathVariable Long id) {
        return new ResponseEntity<>(chapterService.getChapterById(id), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChapter(@PathVariable Long id) {
        chapterService.deleteChapter(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ChapterDto> updateChapter(@PathVariable Long id, @RequestBody @Validated ChapterRequestDto chapterDto) {
        return new ResponseEntity<>(chapterService.updateChapter(id, chapterDto), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UsersDto> getChapterByUserId(@PathVariable Long id) {
        return new ResponseEntity<>(userClient.getUserById(id), HttpStatus.OK);
    }

}
