package com.mini.project.courseservice.service;

import com.mini.project.courseservice.dto.LessonDto;
import com.mini.project.courseservice.dto.LessonFilterDto;
import com.mini.project.courseservice.dto.LessonRequestDto;
import com.mini.project.courseservice.dto.mapper.LessonMapper;
import com.mini.project.courseservice.entity.Chapters;
import com.mini.project.courseservice.entity.Lessons;
import com.mini.project.courseservice.repository.ChaptersRepository;
import com.mini.project.courseservice.repository.LessonsRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LessonsServiceTest {

    @Mock
    private LessonsRepository lessonsRepository;

    @Mock
    private ChaptersRepository chaptersRepository;

    @Mock
    private LessonMapper lessonMapper;

    @InjectMocks
    private LessonsService lessonsService;


    @Test
    void testCreateLesson() {
        LessonRequestDto lessonRequestDto = LessonRequestDto.builder().chapterId(1L).build();
        Lessons lesson = Lessons.builder().build();
        Chapters chapter = Chapters.builder().build();
        LessonDto lessonDto = LessonDto.builder().build();

        when(lessonMapper.toLessons(any(LessonRequestDto.class))).thenReturn(lesson);
        when(chaptersRepository.findById(anyLong())).thenReturn(Optional.of(chapter));
        when(lessonsRepository.save(any(Lessons.class))).thenReturn(lesson);
        when(lessonMapper.toLessonDto(any(Lessons.class))).thenReturn(lessonDto);

        LessonDto result = lessonsService.createLesson(lessonRequestDto);

        assertNotNull(result);
        verify(chaptersRepository, times(1)).findById(anyLong());
        verify(lessonsRepository, times(1)).save(any(Lessons.class));
    }

    @Test
    void testUpdateLesson() {
        LessonRequestDto lessonRequestDto = LessonRequestDto.builder().chapterId(1L).build();
        Lessons lesson = Lessons.builder().build();
        Chapters chapter = Chapters.builder().build();
        LessonDto lessonDto = LessonDto.builder().build();

        when(lessonsRepository.findById(anyLong())).thenReturn(Optional.of(lesson));
        when(chaptersRepository.findById(anyLong())).thenReturn(Optional.of(chapter));
        when(lessonsRepository.save(any(Lessons.class))).thenReturn(lesson);
        when(lessonMapper.toLessonDto(any(Lessons.class))).thenReturn(lessonDto);

        LessonDto result = lessonsService.updateLesson(1L, lessonRequestDto);

        assertNotNull(result);
        verify(lessonsRepository, times(1)).findById(anyLong());
        verify(chaptersRepository, times(1)).findById(anyLong());
        verify(lessonsRepository, times(1)).save(any(Lessons.class));
    }

    @Test
    void testGetLessonById() {
        Lessons lesson = Lessons.builder().build();
        LessonDto lessonDto = LessonDto.builder().build();

        when(lessonsRepository.findById(anyLong())).thenReturn(Optional.of(lesson));
        when(lessonMapper.toLessonDto(any(Lessons.class))).thenReturn(lessonDto);

        LessonDto result = lessonsService.getLessonById(1L);

        assertNotNull(result);
        verify(lessonsRepository, times(1)).findById(anyLong());
    }

    @Test
    void testDeleteLesson() {
        Lessons lesson = Lessons.builder().build();

        when(lessonsRepository.findById(anyLong())).thenReturn(Optional.of(lesson));
        doNothing().when(lessonsRepository).delete(any(Lessons.class));

        lessonsService.deleteLesson(1L);

        verify(lessonsRepository, times(1)).findById(anyLong());
        verify(lessonsRepository, times(1)).delete(any(Lessons.class));
    }

    @Test
    void testGetLesson() {
        LessonFilterDto filter = LessonFilterDto.builder().build();
        Pageable pageable = Pageable.unpaged();
        Lessons lesson = Lessons.builder().build();
        LessonDto lessonDto = LessonDto.builder().build();
        Page<Lessons> page = new PageImpl<>(Collections.singletonList(lesson));

        when(lessonsRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        when(lessonMapper.toLessonDto(any(Lessons.class))).thenReturn(lessonDto);

        Page<LessonDto> result = lessonsService.getLesson(filter, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(lessonsRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }
}