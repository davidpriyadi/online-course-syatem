package com.mini.project.courseservice.service;

import com.mini.project.courseservice.dto.LessonDto;
import com.mini.project.courseservice.dto.LessonFilterDto;
import com.mini.project.courseservice.dto.LessonRequestDto;
import com.mini.project.courseservice.dto.mapper.LessonMapper;
import com.mini.project.courseservice.entity.Lessons;
import com.mini.project.courseservice.repository.ChaptersRepository;
import com.mini.project.courseservice.repository.LessonsRepository;
import com.mini.project.courseservice.repository.jpaspecification.QueryCriteria;
import com.mini.project.courseservice.repository.jpaspecification.QuerySpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LessonsService {

    private final LessonsRepository lessonsRepository;
    private final ChaptersRepository chaptersRepository;
    private final LessonMapper lessonsMapper;

    public LessonDto createLesson(LessonRequestDto lessonDto) {
        var lesson = lessonsMapper.toLessons(lessonDto);
        var chapter = chaptersRepository.findById(lessonDto.getChapterId())
                .orElseThrow(() -> new EntityNotFoundException("Chapter not found"));
        lesson.setChapters(chapter);
        return lessonsMapper.toLessonDto(lessonsRepository.save(lesson));
    }

    public LessonDto updateLesson(Long id, LessonRequestDto lessonDto) {
        var lesson = lessonsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found"));
        var chapter = chaptersRepository.findById(lessonDto.getChapterId())
                .orElseThrow(() -> new EntityNotFoundException("Chapter not found"));
        lesson.setChapters(chapter);
        lessonsMapper.updateEntity(lesson, lessonDto);
        return lessonsMapper.toLessonDto(lessonsRepository.save(lesson));
    }

    public LessonDto getLessonById(Long id) {
        var lesson = lessonsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found"));
        return lessonsMapper.toLessonDto(lesson);
    }

    public void deleteLesson(Long id) {
        var lesson = lessonsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lesson not found"));
        lessonsRepository.delete(lesson);
    }

    public Page<LessonDto> getLesson(LessonFilterDto filter, Pageable pageable) {
        QuerySpecification<Lessons> querySpecification = new QuerySpecification<>();
        querySpecification.add(new QueryCriteria<>("name", filter.getName(), QueryCriteria.CriteriaOperation.MATCH));
        return lessonsRepository.findAll(querySpecification, pageable)
                .map(lessonsMapper::toLessonDto);
    }
}
