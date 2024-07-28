package com.mini.project.order.payment.service.client.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MentorDto implements Serializable {
    private Long id;
    private String name;
    private String profile;
    private String email;
    private String profession;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
