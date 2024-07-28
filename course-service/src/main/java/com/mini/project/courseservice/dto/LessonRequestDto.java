package com.mini.project.courseservice.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.URL;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LessonRequestDto {

    @NotEmpty
    private String name;

    @NotNull
    private Long chapterId;

    @NotEmpty
    @URL
    private String video;

    @NotNull
    private int duration;
}
