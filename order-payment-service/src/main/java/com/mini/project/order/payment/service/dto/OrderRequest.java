package com.mini.project.order.payment.service.dto;

import lombok.Data;

@Data
public class OrderRequest {
    private User user;
    private Course course;

    @Data
    public static class User {
        private Long id;
        private String name;
        private String email;
    }

    @Data
    public static class Course {
        private Long id;
        private String name;
        private Double price;
    }
}