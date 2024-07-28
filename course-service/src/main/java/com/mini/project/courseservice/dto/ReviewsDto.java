package com.mini.project.courseservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewsDto {

    private Long id;

    private Long userId;

    private CoursedDto courses;

    private int rating;

    private String note;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}