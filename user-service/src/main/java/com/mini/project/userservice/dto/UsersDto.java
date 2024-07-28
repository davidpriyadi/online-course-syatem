package com.mini.project.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto implements Serializable {
    private Long id;
    private String email;
    private String name;
    private String address;
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;
//    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updatedAt;
}
