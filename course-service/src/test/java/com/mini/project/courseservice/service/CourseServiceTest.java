package com.mini.project.courseservice.service;

import com.mini.project.courseservice.dto.CourseFilterDto;
import com.mini.project.courseservice.dto.CourseRequestDto;
import com.mini.project.courseservice.dto.CoursedDto;
import com.mini.project.courseservice.dto.mapper.CoursesMapper;
import com.mini.project.courseservice.entity.Courses;
import com.mini.project.courseservice.entity.Mentors;
import com.mini.project.courseservice.repository.CoursesRepository;
import com.mini.project.courseservice.repository.MentorsRepository;
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
class CourseServiceTest {

    @Mock
    private CoursesRepository coursesRepository;

    @Mock
    private MentorsRepository mentorsRepository;

    @Mock
    private CoursesMapper coursesMapper;

    @InjectMocks
    private CourseService courseService;

    @Test
    void testCreateCourse() {
        CourseRequestDto courseRequestDto = CourseRequestDto.builder().mentorId(1L).build();
        Courses course = Courses.builder().build();
        Mentors mentor = Mentors.builder().build();
        CoursedDto coursedDto = CoursedDto.builder().build();

        when(coursesMapper.toCreateEntity(any(CourseRequestDto.class))).thenReturn(course);
        when(mentorsRepository.findById(anyLong())).thenReturn(Optional.of(mentor));
        when(coursesRepository.save(any(Courses.class))).thenReturn(course);
        when(coursesMapper.toDto(any(Courses.class))).thenReturn(coursedDto);

        CoursedDto result = courseService.createCourse(courseRequestDto);

        assertNotNull(result);
        verify(mentorsRepository, times(1)).findById(anyLong());
        verify(coursesRepository, times(1)).save(any(Courses.class));
    }

    @Test
    void testGetById() {
        Courses course = Courses.builder().build();
        CoursedDto coursedDto = CoursedDto.builder().build();

        when(coursesRepository.findById(anyLong())).thenReturn(Optional.of(course));
        when(coursesMapper.toDto(any(Courses.class))).thenReturn(coursedDto);

        CoursedDto result = courseService.getById(1L);

        assertNotNull(result);
        verify(coursesRepository, times(1)).findById(anyLong());
    }

    @Test
    void testUpdateCourse() {
        CourseRequestDto courseRequestDto = CourseRequestDto.builder().mentorId(1L).build();
        Courses course = Courses.builder().build();
        Mentors mentor = Mentors.builder().build();
        CoursedDto coursedDto = CoursedDto.builder().build();

        when(coursesRepository.findById(anyLong())).thenReturn(Optional.of(course));
        when(mentorsRepository.findById(anyLong())).thenReturn(Optional.of(mentor));
        when(coursesRepository.save(any(Courses.class))).thenReturn(course);
        when(coursesMapper.toDto(any(Courses.class))).thenReturn(coursedDto);

        CoursedDto result = courseService.updateCourse(1L, courseRequestDto);

        assertNotNull(result);
        verify(coursesRepository, times(1)).findById(anyLong());
        verify(mentorsRepository, times(1)).findById(anyLong());
        verify(coursesRepository, times(1)).save(any(Courses.class));
    }

    @Test
    void testDeleteCourse() {
        Courses course = Courses.builder().build();

        when(coursesRepository.findById(anyLong())).thenReturn(Optional.of(course));
        doNothing().when(coursesRepository).delete(any(Courses.class));

        courseService.deleteCourse(1L);

        verify(coursesRepository, times(1)).findById(anyLong());
        verify(coursesRepository, times(1)).delete(any(Courses.class));

        // Scenario where course is not found
        when(coursesRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> courseService.deleteCourse(1L));
        verify(coursesRepository, times(2)).findById(anyLong()); // Called twice, once for each scenario
    }

    @Test
    void testGetCourse() {
        CourseFilterDto filter = CourseFilterDto.builder().build();
        Pageable pageable = Pageable.unpaged();
        Courses course = Courses.builder().build();
        CoursedDto coursedDto = CoursedDto.builder().build();
        Page<Courses> page = new PageImpl<>(Collections.singletonList(course));

        when(coursesRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        when(coursesMapper.toDto(any(Courses.class))).thenReturn(coursedDto);

        Page<CoursedDto> result = courseService.getCourse(filter, pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(coursesRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }
}