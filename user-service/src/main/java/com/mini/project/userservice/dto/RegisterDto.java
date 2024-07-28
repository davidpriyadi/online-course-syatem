package com.mini.project.userservice.dto;

import com.mini.project.userservice.validation.constrain.EmailIsExist;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {

    @NotEmpty
    @EmailIsExist
    @Email
    private String email;
    @NotEmpty
    private String name;
    @NotEmpty
    private String password;
    private String address;
}
