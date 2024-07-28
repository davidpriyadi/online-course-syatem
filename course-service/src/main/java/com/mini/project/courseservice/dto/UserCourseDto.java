package com.mini.project.courseservice.dto;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCourseDto {

    private Long id;

    private CoursedDto courses;

    private Long userId;
}
