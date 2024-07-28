package com.mini.project.order.payment.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private Map<String, List<String>> details;
}