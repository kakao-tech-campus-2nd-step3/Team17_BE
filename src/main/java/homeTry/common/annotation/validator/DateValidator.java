package homeTry.common.annotation.validator;

import homeTry.common.annotation.DateValid;
import homeTry.team.dto.DateDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.util.Arrays;

public class DateValidator implements ConstraintValidator<DateValid, DateDTO> {
    private final int[] monthsWith30Days = {4, 6, 9, 11};
    private static final int FEBRUARY = 2;
    private static final int MAX_DAYS_IN_FEBRUARY_LEAP_YEAR = 29;
    private static final int MAX_DAYS_IN_FEBRUARY_NON_LEAP_YEAR = 28;
    private static final int MAX_DAYS_IN_30_DAY_MONTH = 30;

    @Override
    public boolean isValid(DateDTO value, ConstraintValidatorContext context) {
        LocalDate today = LocalDate.now();
        LocalDate valueDate = LocalDate.of(value.year(), value.month(), value.day());

        if (valueDate.isAfter(today)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("오늘보다 이후 날짜는 조회가 안됩니다")
                    .addConstraintViolation();
            return false;
        }

        if (value.month() == FEBRUARY) { //2월에 대한 day 유효성 검사
            if (isLeapYear(value.year()) && value.day() > MAX_DAYS_IN_FEBRUARY_LEAP_YEAR) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("윤년인 경우 2월은 최대 29일 입니다")
                        .addConstraintViolation();
                return false;
            }

            if (value.day() > MAX_DAYS_IN_FEBRUARY_NON_LEAP_YEAR) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("2월은 최대 28일 입니다")
                        .addConstraintViolation();
                return false;
            }
        }

        if (Arrays.stream(monthsWith30Days).anyMatch(month -> month == value.month())) {
            if (value.day() > MAX_DAYS_IN_30_DAY_MONTH) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(value.month() + "월은 최대 30일 입니다")
                        .addConstraintViolation();
                return false;
            }
        }

        return true;
    }

    private boolean isLeapYear(int year) {
        return year % 4 == 0;
    }
}
