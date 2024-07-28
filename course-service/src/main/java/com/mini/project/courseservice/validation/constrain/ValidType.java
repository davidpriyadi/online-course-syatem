package com.mini.project.courseservice.validation.constrain;

import com.mini.project.courseservice.validation.TypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = TypeValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidType {
    String message() default "Invalid type";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}