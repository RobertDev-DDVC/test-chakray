package com.test.chakray.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RfcValidator.class)
public @interface RFC {
    String message() default "Invalid RFC format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
