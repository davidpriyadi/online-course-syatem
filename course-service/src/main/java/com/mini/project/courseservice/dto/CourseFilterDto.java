package com.mini.project.courseservice.dto;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseFilterDto {

    private String name;

    private String type;

    private String level;

}
