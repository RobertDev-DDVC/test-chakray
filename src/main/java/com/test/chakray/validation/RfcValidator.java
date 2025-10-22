package com.test.chakray.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RfcValidator implements ConstraintValidator<RFC, String> {

    private static final Pattern RFC_PATTERN =
            Pattern.compile("^([A-ZÑ&]{3,4})(\\d{6})([A-Z0-9]{3})$", Pattern.CASE_INSENSITIVE);

    @Override
    public boolean isValid(String value, ConstraintValidatorContext ctx) {
        if (value == null || value.isBlank()) return true;
        String rfc = value.trim().toUpperCase(Locale.ROOT);

        Matcher m = RFC_PATTERN.matcher(rfc);
        if (!m.matches()) return false;

        String fecha = m.group(2);

        String yy = fecha.substring(0, 2);
        String mm = fecha.substring(2, 4);
        String dd = fecha.substring(4, 6);

        int year = Integer.parseInt(yy);
        year += (year >= 50 ? 1900 : 2000);

        try {
            LocalDate.parse(
                    year + "-" + mm + "-" + dd,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd")
            );
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
