package com.mini.project.courseservice.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCourseRequestDto {

    private Long courseId;

    private Long userId;
}
