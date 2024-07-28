package com.mini.project.courseservice.dto.mapper;

import com.mini.project.courseservice.dto.LessonDto;
import com.mini.project.courseservice.dto.LessonRequestDto;
import com.mini.project.courseservice.entity.Lessons;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LessonMapper {
    LessonDto toLessonDto(Lessons lessons);

    Lessons toLessons(LessonRequestDto lessonRequestDto);

    void updateEntity(@MappingTarget Lessons lessons, LessonRequestDto lessonRequestDto);
}
