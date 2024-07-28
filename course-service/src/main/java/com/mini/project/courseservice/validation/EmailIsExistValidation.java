package com.mini.project.courseservice.validation;

import com.mini.project.courseservice.repository.MentorsRepository;
import com.mini.project.courseservice.validation.constrain.EmailIsExist;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmailIsExistValidation implements ConstraintValidator<EmailIsExist, String> {

    private final MentorsRepository mentorsRepository;
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return !mentorsRepository.existsUsersByEmail(email);
    }
}
