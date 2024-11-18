package homeTry.common.annotation.validator;

import homeTry.common.annotation.PasswordValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordValid, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {


        if (value == null || !value.isBlank())
            return true;

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("비밀번호는 비어있거나 빈 공백으로 이루어지면 안됩니다")
                .addConstraintViolation();
        return false;
    }
}
