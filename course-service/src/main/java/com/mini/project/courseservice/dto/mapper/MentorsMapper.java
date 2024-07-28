package com.mini.project.courseservice.dto.mapper;

import com.mini.project.courseservice.dto.MentorDto;
import com.mini.project.courseservice.dto.MentorRequestDto;
import com.mini.project.courseservice.entity.Mentors;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface MentorsMapper {
    MentorDto toDto(Mentors mentors);
    Mentors toEntity(MentorDto mentorDto);
    Mentors toCreateEntity(MentorRequestDto mentorRequestDto);
    void updateEntity(@MappingTarget Mentors mentor, MentorRequestDto mentorRequestDto);
}
