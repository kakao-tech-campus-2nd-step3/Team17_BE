package homeTry.mainPage.dto.request;

import java.time.DateTimeException;
import java.time.LocalDate;

import homeTry.mainPage.exception.BadRequestException.InvalidDateException;

public record MainPageRequest(
        int year,
        int month,
        int day) {

    public MainPageRequest() {
        this(LocalDate.now().getYear(),
                LocalDate.now().getMonthValue(),
                LocalDate.now().getDayOfMonth());

    }

    public MainPageRequest(int year, int month, int day) {
        validateDay(year, month, day);
        this.year = year;
        this.month = month;
        this.day = day;
    }

    private static void validateDay(int year, int month, int day) {
        try {
            LocalDate.of(year, month, day);
        } catch (DateTimeException e) {
            throw new InvalidDateException();
        }
    }

    public LocalDate toDate() {
        return LocalDate.of(year, month, day);
    }
}
