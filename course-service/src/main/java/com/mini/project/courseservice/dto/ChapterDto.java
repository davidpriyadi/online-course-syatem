package com.mini.project.courseservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChapterDto {
    private Long id;

    private String name;

    private CoursedDto courses;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
