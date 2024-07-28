package com.mini.project.courseservice.service;

import com.mini.project.courseservice.client.UserClient;
import com.mini.project.courseservice.client.dto.UsersDto;
import com.mini.project.courseservice.dto.UserCourseDto;
import com.mini.project.courseservice.dto.UserCourseRequestDto;
import com.mini.project.courseservice.dto.mapper.UserCoursesMapper;
import com.mini.project.courseservice.entity.Courses;
import com.mini.project.courseservice.entity.UsersCourses;
import com.mini.project.courseservice.repository.CoursesRepository;
import com.mini.project.courseservice.repository.UserCoursesRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserCourseServiceTest {

    @Mock
    private CoursesRepository courseRepository;

    @Mock
    private UserClient userClient;

    @Mock
    private UserCoursesRepository userCourseRepository;

    @Mock
    private UserCoursesMapper userCoursesMapper;

    @InjectMocks
    private UserCourseService userCourseService;

    @Test
    void testCreate() {
        UserCourseRequestDto requestDto = UserCourseRequestDto.builder().courseId(1L).userId(1L).build();
        Courses course = Courses.builder().type("basic").build();
        UsersCourses usersCourses = UsersCourses.builder().build();

        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));
        when(userClient.getUserById(anyLong())).thenReturn(UsersDto.builder().id(1L).build());
        when(userCourseRepository.existsByCoursesIdAndUserId(anyLong(), anyLong())).thenReturn(false);
        when(userCourseRepository.save(any(UsersCourses.class))).thenReturn(usersCourses);

        userCourseService.create(requestDto);

        verify(courseRepository, times(1)).findById(anyLong());
        verify(userClient, times(1)).getUserById(anyLong());
        verify(userCourseRepository, times(1)).existsByCoursesIdAndUserId(anyLong(), anyLong());
        verify(userCourseRepository, times(1)).save(any(UsersCourses.class));
    }

    @Test
    void testCreateCourseNotFound() {
        UserCourseRequestDto requestDto = UserCourseRequestDto.builder().courseId(1L).userId(1L).build();

        when(courseRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userCourseService.create(requestDto));
        verify(courseRepository, times(1)).findById(anyLong());
    }

    @Test
    void testCreateUserAlreadyHasCourse() {
        UserCourseRequestDto requestDto = UserCourseRequestDto.builder().courseId(1L).userId(1L).build();
        Courses course = Courses.builder().type("basic").build();

        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));
        when(userClient.getUserById(anyLong())).thenReturn(UsersDto.builder().id(1L).build());
        when(userCourseRepository.existsByCoursesIdAndUserId(anyLong(), anyLong())).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> userCourseService.create(requestDto));
        verify(courseRepository, times(1)).findById(anyLong());
        verify(userClient, times(1)).getUserById(anyLong());
        verify(userCourseRepository, times(1)).existsByCoursesIdAndUserId(anyLong(), anyLong());
    }

    @Test
    void testCreatePremium() {
        UserCourseRequestDto requestDto = UserCourseRequestDto.builder().courseId(1L).userId(1L).build();
        Courses course = Courses.builder().type("premium").price(100).build();
        UsersCourses usersCourses = UsersCourses.builder().build();
        UserCourseDto userCourseDto = UserCourseDto.builder().build();

        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));
        when(userClient.getUserById(anyLong())).thenReturn(UsersDto.builder().id(1L).build());
        when(userCourseRepository.existsByCoursesIdAndUserId(anyLong(), anyLong())).thenReturn(false);
        when(userCourseRepository.save(any(UsersCourses.class))).thenReturn(usersCourses);
        when(userCoursesMapper.toDto(any(UsersCourses.class))).thenReturn(userCourseDto);

        UserCourseDto result = userCourseService.createPremium(requestDto);

        assertNotNull(result);
        verify(courseRepository, times(1)).findById(anyLong());
        verify(userClient, times(1)).getUserById(anyLong());
        verify(userCourseRepository, times(1)).existsByCoursesIdAndUserId(anyLong(), anyLong());
        verify(userCourseRepository, times(1)).save(any(UsersCourses.class));
        verify(userCoursesMapper, times(1)).toDto(any(UsersCourses.class));
    }

    @Test
    void testCreatePremiumCourseNotFound() {
        UserCourseRequestDto requestDto = UserCourseRequestDto.builder().courseId(1L).userId(1L).build();

        when(courseRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userCourseService.createPremium(requestDto));
        verify(courseRepository, times(1)).findById(anyLong());
    }

    @Test
    void testCreatePremiumUserAlreadyHasCourse() {
        UserCourseRequestDto requestDto = UserCourseRequestDto.builder().courseId(1L).userId(1L).build();
        Courses course = Courses.builder().type("premium").price(100).build();

        when(courseRepository.findById(anyLong())).thenReturn(Optional.of(course));
        when(userClient.getUserById(anyLong())).thenReturn(UsersDto.builder().id(1L).build());
        when(userCourseRepository.existsByCoursesIdAndUserId(anyLong(), anyLong())).thenReturn(true);

        assertThrows(ResponseStatusException.class, () -> userCourseService.createPremium(requestDto));
        verify(courseRepository, times(1)).findById(anyLong());
        verify(userClient, times(1)).getUserById(anyLong());
        verify(userCourseRepository, times(1)).existsByCoursesIdAndUserId(anyLong(), anyLong());
    }
}