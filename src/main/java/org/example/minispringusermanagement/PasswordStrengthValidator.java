package org.example.minispringusermanagement;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordStrengthValidator implements ConstraintValidator<PasswordStrength, String> {

    @Override
    public void initialize(PasswordStrength constraintAnnotation) {
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        // Password must be at least 8 characters long and contain at least one digit, one lowercase, one uppercase, and one special character
        return password != null && password.length() >= 8 &&
                password.matches(".*\\d.*") &&
                password.matches(".*[a-z].*") &&
                password.matches(".*[A-Z].*") &&
                password.matches(".*[!@#$%^&*(),.?\":{}|<>].*");
    }
}
