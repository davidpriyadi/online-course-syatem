package com.mini.project.userservice.validation;

import com.mini.project.userservice.repository.UsersRepository;
import com.mini.project.userservice.validation.constrain.EmailIsExist;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmailIsExistValidation implements ConstraintValidator<EmailIsExist, String> {

    private final UsersRepository usersRepository;
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return !usersRepository.existsUsersByEmail(email);
    }
}
