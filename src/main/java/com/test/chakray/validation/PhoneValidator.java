package com.test.chakray.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<Phone, String> {
    private boolean allowCountryCode;

    @Override
    public void initialize(Phone constraintAnnotation) {
        this.allowCountryCode = constraintAnnotation.allowCountryCode();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext ctx) {
        if (value == null || value.isBlank()) return true;

        String digits = value.replaceAll("\\D", "");

        if (!allowCountryCode) return digits.length() == 10;

        if (digits.length() < 10) return false;

        String national10 = digits.substring(digits.length() - 10);
        if (national10.length() != 10) return false;

        return true;
    }
}
