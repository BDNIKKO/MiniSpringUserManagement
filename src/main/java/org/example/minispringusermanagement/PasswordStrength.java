package org.example.minispringusermanagement;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordStrengthValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordStrength {
    String message() default "Password must be at least 8 characters long and include at least one digit, one lowercase letter, one uppercase letter, and one special character.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
