package com.mini.project.courseservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LessonDto {

    private Long id;

    private String name;

    private ChapterDto chapters;

    private String video;

    private int duration;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
