package com.mini.project.courseservice.service;

import com.mini.project.courseservice.dto.ChapterDto;
import com.mini.project.courseservice.dto.ChapterFilter;
import com.mini.project.courseservice.dto.ChapterRequestDto;
import com.mini.project.courseservice.dto.mapper.ChapterMapper;
import com.mini.project.courseservice.entity.Chapters;
import com.mini.project.courseservice.entity.Courses;
import com.mini.project.courseservice.repository.ChaptersRepository;
import com.mini.project.courseservice.repository.CoursesRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChapterServiceTest {

    @Mock
    private ChaptersRepository chaptersRepository;

    @Mock
    private CoursesRepository coursesRepository;

    @Mock
    private ChapterMapper chapterMapper;

    @InjectMocks
    private ChapterService chapterService;


    @Test
    void testCreateChapter() {
        ChapterRequestDto chapterRequestDto = ChapterRequestDto.builder().coursesId(1L).build();
        Chapters chapter = Chapters.builder().build();
        Courses course = Courses.builder().build();
        ChapterDto chapterDto = ChapterDto.builder().build();

        when(chapterMapper.toChapter(any(ChapterRequestDto.class))).thenReturn(chapter);
        when(coursesRepository.findById(anyLong())).thenReturn(Optional.of(course));
        when(chaptersRepository.save(any(Chapters.class))).thenReturn(chapter);
        when(chapterMapper.toChapterDto(any(Chapters.class))).thenReturn(chapterDto);

        ChapterDto result = chapterService.createChapter(chapterRequestDto);

        assertNotNull(result);
        verify(coursesRepository, times(1)).findById(anyLong());
        verify(chaptersRepository, times(1)).save(any(Chapters.class));
    }

    @Test
    void testGetChapterById() {
        Chapters chapter = Chapters.builder().build();
        ChapterDto chapterDto = ChapterDto.builder().build();

        when(chaptersRepository.findById(anyLong())).thenReturn(Optional.of(chapter));
        when(chapterMapper.toChapterDto(any(Chapters.class))).thenReturn(chapterDto);

        ChapterDto result = chapterService.getChapterById(1L);

        assertNotNull(result);
        verify(chaptersRepository, times(1)).findById(anyLong());
    }

    @Test
    void testGetChapter() {
        ChapterFilter filter = ChapterFilter.builder().build();
        Pageable pageable = Pageable.unpaged();
        Chapters chapter = Chapters.builder().build();
        ChapterDto chapterDto = ChapterDto.builder().build();
        Page<Chapters> page = new PageImpl<>(Collections.singletonList(chapter));

        when(chaptersRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        when(chapterMapper.toChapterDto(any(Chapters.class))).thenReturn(chapterDto);

        Page<ChapterDto> result = chapterService.getChapter(filter, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(chaptersRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    void testDeleteChapter() {
        // Scenario where chapter is found
        Chapters chapter = Chapters.builder().build();
        when(chaptersRepository.findById(anyLong())).thenReturn(Optional.of(chapter));
        doNothing().when(chaptersRepository).delete(any(Chapters.class));

        chapterService.deleteChapter(1L);

        verify(chaptersRepository, times(1)).findById(anyLong());
        verify(chaptersRepository, times(1)).delete(any(Chapters.class));

        // Scenario where chapter is not found
        when(chaptersRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> chapterService.deleteChapter(1L));
        verify(chaptersRepository, times(2)).findById(anyLong()); // Called twice, once for each scenario
    }

    @Test
    void testUpdateChapter() {
        ChapterRequestDto chapterRequestDto = ChapterRequestDto.builder().coursesId(1L).build();
        Chapters chapter = Chapters.builder().build();
        Courses course = Courses.builder().build();
        ChapterDto chapterDto = ChapterDto.builder().build();

        when(chaptersRepository.findById(anyLong())).thenReturn(Optional.of(chapter));
        when(coursesRepository.findById(anyLong())).thenReturn(Optional.of(course));
        when(chaptersRepository.save(any(Chapters.class))).thenReturn(chapter);
        when(chapterMapper.toChapterDto(any(Chapters.class))).thenReturn(chapterDto);

        ChapterDto result = chapterService.updateChapter(1L, chapterRequestDto);

        assertNotNull(result);
        verify(chaptersRepository, times(1)).findById(anyLong());
        verify(coursesRepository, times(1)).findById(anyLong());
        verify(chaptersRepository, times(1)).save(any(Chapters.class));
    }
}