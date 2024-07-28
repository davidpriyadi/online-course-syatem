package com.mini.project.order.payment.service.client.dto;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CoursedDto implements Serializable {

    private Long id;

    private String name;

    private boolean certificate;

    private String thumbnail;

    private String type;

    private String status;

    private int price;

    private String level;

    private String description;

    private MentorDto mentors;

}