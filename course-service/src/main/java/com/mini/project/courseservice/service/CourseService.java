package com.mini.project.courseservice.service;

import com.mini.project.courseservice.dto.CourseFilterDto;
import com.mini.project.courseservice.dto.CourseRequestDto;
import com.mini.project.courseservice.dto.CoursedDto;
import com.mini.project.courseservice.dto.mapper.CoursesMapper;
import com.mini.project.courseservice.entity.Courses;
import com.mini.project.courseservice.repository.CoursesRepository;
import com.mini.project.courseservice.repository.MentorsRepository;
import com.mini.project.courseservice.repository.jpaspecification.QueryCriteria;
import com.mini.project.courseservice.repository.jpaspecification.QuerySpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseService {

    private final CoursesRepository coursesRepository;
    private final MentorsRepository mentorsRepository;
    private final CoursesMapper coursesMapper;


    @CachePut(value = "courses", key = "#result.id")
    public CoursedDto createCourse(CourseRequestDto courseDto) {
        var course = coursesMapper.toCreateEntity(courseDto);
        var mentor = mentorsRepository.findById(courseDto.getMentorId())
                .orElseThrow(() -> new EntityNotFoundException("Mentor not found"));
        course.setMentors(mentor);
        return coursesMapper.toDto(coursesRepository.save(course));
    }

    @Cacheable(value = "courses", key = "#id")
    public CoursedDto getById(Long id) {
        var course = coursesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));
        return coursesMapper.toDto(course);
    }

    @CacheEvict(value = "courses", key = "#id")
    @CachePut(value = "courses", key = "#id")
    public CoursedDto updateCourse(Long id, CourseRequestDto courseDto) {
        var course = coursesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));
        var mentor = mentorsRepository.findById(courseDto.getMentorId())
                .orElseThrow(() -> new EntityNotFoundException("Mentor not found"));
        coursesMapper.updateEntity(course, courseDto);
        course.setMentors(mentor);
        return coursesMapper.toDto(coursesRepository.save(course));
    }

    @CacheEvict(value = "courses", key = "#id")
    public void deleteCourse(Long id) {
        var course = coursesRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));
        coursesRepository.delete(course);
    }

    public Page<CoursedDto> getCourse(CourseFilterDto filterDto, Pageable pageable) {
        QuerySpecification<Courses> querySpecification = new QuerySpecification<>();
        querySpecification.add(new QueryCriteria<>("name", filterDto.getName(), QueryCriteria.CriteriaOperation.MATCH));
        querySpecification.add(new QueryCriteria<>("type", filterDto.getType(), QueryCriteria.CriteriaOperation.EQUAL));
        querySpecification.add(new QueryCriteria<>("level", filterDto.getLevel(), QueryCriteria.CriteriaOperation.EQUAL));
        return coursesRepository.findAll(querySpecification, pageable)
                .map(coursesMapper::toDto);
    }
}
