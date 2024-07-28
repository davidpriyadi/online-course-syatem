package com.mini.project.order.payment.service.client.dto;

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
