package com.mini.project.courseservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewsRequestDto {

    @NotNull
    private Long userId;

    @NotNull
    private Long courseId;

    @Min(1)
    @Max(5)
    private int rating;

    private String note;

}