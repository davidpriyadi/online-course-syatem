package com.mini.project.courseservice.dto.mapper;

import com.mini.project.courseservice.dto.ChapterDto;
import com.mini.project.courseservice.dto.ChapterRequestDto;
import com.mini.project.courseservice.entity.Chapters;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ChapterMapper {
    ChapterDto toChapterDto(Chapters chapter);

    Chapters toChapter(ChapterRequestDto chapterRequestDto);

    void updateEntity(@MappingTarget Chapters chapters, ChapterRequestDto courseRequestDto);
}
