package com.projet.fatma.utils.tools.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SecurePassword {
    String message() default "Password must contain at least one uppercase letter, one lowercase letter, one number, one special character and should be at least 8 characters long.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}