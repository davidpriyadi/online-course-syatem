package com.mini.project.courseservice.validation;

import com.mini.project.courseservice.validation.constrain.ValidType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class TypeValidator implements ConstraintValidator<ValidType, String> {

    private final List<String> validTypes = Arrays.asList("free", "premium");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return validTypes.contains(value);
    }
}