package com.mini.project.courseservice.validation.constrain;

import com.mini.project.courseservice.validation.LevelValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = LevelValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidLevel {
    String message() default "Invalid level";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}