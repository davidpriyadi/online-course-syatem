package com.mini.project.order.payment.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {

    private Long id;

    private String status;

    private Long userId;

    private Long courseId;

    private String snapUrl;

    private Map<String, Object> metadata;

}
