package homeTry.common.constants;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public final class DateTimeUtil {

    private static final LocalTime START_OF_DAY_TIME = LocalTime.of(3, 0, 0);
    private static final LocalTime END_OF_DAY_TIME = LocalTime.of(2, 59, 59);

    private DateTimeUtil() {
    }

    // 하루의 시작과 끝
    public static LocalDateTime getStartOfDay(LocalDate date) {
        return date.atTime(START_OF_DAY_TIME);
    }

    public static LocalDateTime getEndOfDay(LocalDate date) {
        return date.plusDays(1).atTime(END_OF_DAY_TIME);
    }

    // 현재 시간 조정
    public static LocalDate getAdjustedCurrentDate() {
        LocalDate currentDate = LocalDate.now();

        // 3시 이전이면 date를 전날로 수정
        if (LocalTime.now().isBefore(LocalTime.of(3, 0, 0))) {
            currentDate = currentDate.minusDays(1);
        }

        return currentDate;
    }

    public static LocalDateTime[] getAdjustedDateRange() {
        LocalDate currentDate = getAdjustedCurrentDate();

        LocalDateTime startOfDay = getStartOfDay(currentDate);
        LocalDateTime endOfDay = getEndOfDay(currentDate);

        return new LocalDateTime[]{startOfDay, endOfDay};
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
