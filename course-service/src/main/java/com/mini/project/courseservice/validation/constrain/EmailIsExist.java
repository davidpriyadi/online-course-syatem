package com.mini.project.courseservice.validation.constrain;

import com.mini.project.courseservice.validation.EmailIsExistValidation;
import jakarta.validation.Constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = EmailIsExistValidation.class)
public @interface EmailIsExist {
    /**
     *
     * @return String
     */
    String message() default "Email already exists";

    /**
     *
     * @return class
     */
    Class<?>[] groups() default {};

    /**
     *
     * @return class
     */
    Class<?>[] payload() default {};
}
