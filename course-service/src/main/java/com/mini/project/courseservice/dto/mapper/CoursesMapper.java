package com.mini.project.courseservice.dto.mapper;

import com.mini.project.courseservice.dto.CourseRequestDto;
import com.mini.project.courseservice.dto.CoursedDto;
import com.mini.project.courseservice.entity.Courses;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CoursesMapper {
    Courses toCreateEntity(CourseRequestDto courseRequestDto);
    CoursedDto toDto(Courses courses);
    void updateEntity(@MappingTarget Courses mentor, CourseRequestDto mentorRequestDto);
}
