package com.mini.project.courseservice.service;

import com.mini.project.courseservice.client.UserClient;
import com.mini.project.courseservice.dto.UserCourseDto;
import com.mini.project.courseservice.dto.UserCourseRequestDto;
import com.mini.project.courseservice.dto.mapper.UserCoursesMapper;
import com.mini.project.courseservice.entity.UsersCourses;
import com.mini.project.courseservice.repository.CoursesRepository;
import com.mini.project.courseservice.repository.UserCoursesRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCourseService {

    private final CoursesRepository courseRepository;
    private final UserClient userClient;
    private final UserCoursesRepository userCourseRepository;
    private final UserCoursesMapper userCoursesMapper;

    public void create(UserCourseRequestDto requestDto) {

        var course = courseRepository.findById(requestDto.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        var user = userClient.getUserById(requestDto.getUserId());

        var exists = userCourseRepository.existsByCoursesIdAndUserId(requestDto.getCourseId(), user.getId());

        // "Bisa dibikin validator kedepannya"
        if (exists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already has this course");
        }

        // "Bisa dibikin validator kedepannya"
        if ("premium".equals(course.getType())) {
            if (course.getPrice() == 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Price can't be 0");
            }

            //rest ke order
        } else {
            userCourseRepository.save(UsersCourses.builder()
                    .courses(course)
                    .userId(requestDto.getUserId())
                    .build());
        }
    }

    public UserCourseDto createPremium(UserCourseRequestDto requestDto) {
        var course = courseRepository.findById(requestDto.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        var user = userClient.getUserById(requestDto.getUserId());

        var exists = userCourseRepository.existsByCoursesIdAndUserId(requestDto.getCourseId(), user.getId());

        if (exists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User already has this course");
        }
        return userCoursesMapper.toDto(
                userCourseRepository.save(UsersCourses.builder()
                        .courses(course)
                        .userId(requestDto.getUserId())
                        .build()));
    }
}
