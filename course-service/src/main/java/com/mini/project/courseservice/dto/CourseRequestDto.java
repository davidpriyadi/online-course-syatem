package com.mini.project.courseservice.dto;

import com.mini.project.courseservice.validation.constrain.ValidLevel;
import com.mini.project.courseservice.validation.constrain.ValidStatus;
import com.mini.project.courseservice.validation.constrain.ValidType;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.URL;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequestDto {

    @NotEmpty
    private String name;

    private boolean certificate;

    @NotEmpty
    @URL
    private String thumbnail;

    @NotEmpty
    @ValidType
    private String type;

    @NotEmpty
    @ValidStatus
    private String status;

    private int price;

    @NotEmpty
    @ValidLevel
    private String level;

    private String description;

    private Long mentorId;
}
