package homeTry.mainPage.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.time.LocalDate;

public record MainPageRequest(
        @Min(value = 2024, message = "년도의 최소값은 2024 입니다 ")
        int year,

        @Min(value = 1, message = "월은 1월부터 시작입니다")
        @Max(value = 12, message = "월은 12월까지 입니다 ")
        int month,

        @Min(value = 1, message = "일은 1일부터 시작입니다")
        @Max(value = 31, message = "일은 31일을 넘어갈 수 없습니다")
        int day) {

    public MainPageRequest() {
        this(LocalDate.now().getYear(),
                LocalDate.now().getMonthValue(),
                LocalDate.now().getDayOfMonth());

    }

    public LocalDate toDate() {
        return LocalDate.of(year, month, day);
    }

    public boolean isToday(LocalDate date) {
        return date.equals(LocalDate.now());
    }
}
