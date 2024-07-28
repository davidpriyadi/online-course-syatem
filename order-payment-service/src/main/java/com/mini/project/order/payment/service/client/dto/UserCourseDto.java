package com.mini.project.order.payment.service.client.dto;


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
