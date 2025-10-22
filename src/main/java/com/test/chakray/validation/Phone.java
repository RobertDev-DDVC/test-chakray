package com.test.chakray.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneValidator.class)
public @interface Phone {
    String message() default "Invalid phone number";
    boolean allowCountryCode() default true;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
