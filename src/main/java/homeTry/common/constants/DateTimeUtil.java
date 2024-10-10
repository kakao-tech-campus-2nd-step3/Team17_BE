package homeTry.common.constants;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public final class DateTimeUtil {

    private DateTimeUtil() {}

    private static final LocalTime START_OF_DAY_TIME = LocalTime.of(3, 0, 0);
    private static final LocalTime END_OF_DAY_TIME = LocalTime.of(2, 59, 59);

    // 하루의 시작과 끝
    public static LocalDateTime getStartOfDay(LocalDate date) {
        return date.atTime(START_OF_DAY_TIME);
    }

    public static LocalDateTime getEndOfDay(LocalDate date) {
        return date.plusDays(1).atTime(END_OF_DAY_TIME);
    }

    // 주의 시작과 끝
    public static LocalDateTime getStartOfWeek(LocalDate date) {
        LocalDate startOfWeek = date.minusDays(date.getDayOfWeek().getValue() - 1);
        return startOfWeek.atTime(START_OF_DAY_TIME);
    }

    public static LocalDateTime getEndOfWeek(LocalDate date) {
        LocalDate startOfWeek = date.minusDays(date.getDayOfWeek().getValue() - 1);
        return startOfWeek.plusDays(6).atTime(END_OF_DAY_TIME);
    }

    // 달의 시작과 끝
    public static LocalDateTime getStartOfMonth(LocalDate date) {
        LocalDate startOfMonth = date.withDayOfMonth(1);
        return startOfMonth.atTime(START_OF_DAY_TIME);
    }

    public static LocalDateTime getEndOfMonth(LocalDate date) {
        LocalDate startOfMonth = date.withDayOfMonth(1);
        return startOfMonth.plusMonths(1).minusDays(1).atTime(END_OF_DAY_TIME);
    }

}
