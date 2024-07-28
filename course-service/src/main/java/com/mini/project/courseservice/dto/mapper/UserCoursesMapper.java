package com.mini.project.courseservice.dto.mapper;

import com.mini.project.courseservice.dto.CourseRequestDto;
import com.mini.project.courseservice.dto.UserCourseDto;
import com.mini.project.courseservice.entity.UsersCourses;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserCoursesMapper {
    UsersCourses toCreateEntity(CourseRequestDto courseRequestDto);
    UserCourseDto toDto(UsersCourses courses);
}
