package com.mini.project.courseservice.validation;

import com.mini.project.courseservice.validation.constrain.ValidLevel;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class LevelValidator implements ConstraintValidator<ValidLevel, String> {

    private final List<String> validLevels = Arrays.asList("all-level", "beginner", "intermediate", "advance");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return validLevels.contains(value);
    }
}