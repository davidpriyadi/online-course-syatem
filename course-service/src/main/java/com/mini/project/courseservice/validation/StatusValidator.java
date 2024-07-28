package com.mini.project.courseservice.validation;

import com.mini.project.courseservice.validation.constrain.ValidStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class StatusValidator implements ConstraintValidator<ValidStatus, String> {

    private final List<String> validStatuses = Arrays.asList("draft", "published");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return validStatuses.contains(value);
    }
}