package homeTry.common.annotation.validator;

import homeTry.common.annotation.DateValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class DateValidator implements ConstraintValidator<DateValid, LocalDate> {
    public static final int startYear = 2024;
    public static final int startMonth = 9;
    public static final int startDay = 1;


    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        LocalDate today = LocalDate.now();
        LocalDate serviceStartDate = LocalDate.of(startYear, startMonth, startDay);

        if (value.isAfter(today)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("오늘보다 이후 날짜는 조회가 안됩니다")
                    .addConstraintViolation();
            return false;
        }

        if (value.isBefore(serviceStartDate)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("서비스 시작일보다 이전 날짜는 조회가 안됩니다")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
