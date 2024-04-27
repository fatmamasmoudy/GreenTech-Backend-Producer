package com.projet.fatma.utils.tools.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.regex.Pattern;
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PasswordValidator implements ConstraintValidator<SecurePassword, String> {

    private static final String PASSWORD_PATTERN =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return pattern.matcher(password).matches();
    }
}
