package com.mini.project.courseservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChapterRequestDto {

    @NotEmpty
    private String name;

    @NotNull

    private Long coursesId;

}
