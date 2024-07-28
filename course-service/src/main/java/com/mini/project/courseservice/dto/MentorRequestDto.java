package com.mini.project.courseservice.dto;

import com.mini.project.courseservice.validation.constrain.EmailIsExist;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MentorRequestDto {
    @NotEmpty
    private String name;
    @NotEmpty
    private String profile;
    @NotEmpty
    @Email
    @EmailIsExist
    private String email;
    @NotEmpty
    private String profession;
}
