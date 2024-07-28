package com.mini.project.courseservice.service;

import com.mini.project.courseservice.dto.ChapterDto;
import com.mini.project.courseservice.dto.ChapterFilter;
import com.mini.project.courseservice.dto.ChapterRequestDto;
import com.mini.project.courseservice.dto.mapper.ChapterMapper;
import com.mini.project.courseservice.entity.Chapters;
import com.mini.project.courseservice.repository.ChaptersRepository;
import com.mini.project.courseservice.repository.CoursesRepository;
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
public class ChapterService {

    private final ChaptersRepository chaptersRepository;
    private final CoursesRepository coursesRepository;
    private final ChapterMapper chapterMapper;


    public ChapterDto createChapter(ChapterRequestDto chapterDto) {
        var chapter = chapterMapper.toChapter(chapterDto);
        var course = coursesRepository.findById(chapterDto.getCoursesId())
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));
        chapter.setCourses(course);
        return chapterMapper.toChapterDto(chaptersRepository.save(chapter));
    }


    public ChapterDto getChapterById(Long id) {
        var chapter = chaptersRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Chapter not found"));
        return chapterMapper.toChapterDto(chapter);
    }

    public Page<ChapterDto> getChapter(ChapterFilter filter, Pageable pageable) {
        QuerySpecification<Chapters> querySpecification = new QuerySpecification<>();
        querySpecification.add(new QueryCriteria<>("name", filter.getName(), QueryCriteria.CriteriaOperation.MATCH));
        return chaptersRepository.findAll(querySpecification, pageable)
                .map(chapterMapper::toChapterDto);
    }

    public void deleteChapter(Long id) {
        var chapter = chaptersRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Chapter not found"));
        chaptersRepository.delete(chapter);
    }

    public ChapterDto updateChapter(Long id, ChapterRequestDto chapterDto) {
        var chapter = chaptersRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Chapter not found"));
        var course = coursesRepository.findById(chapterDto.getCoursesId())
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));
        chapterMapper.updateEntity(chapter, chapterDto);
        chapter.setCourses(course);
        return chapterMapper.toChapterDto(chaptersRepository.save(chapter));
    }
}
