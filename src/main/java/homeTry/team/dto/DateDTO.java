package homeTry.team.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.time.LocalDate;

public record DateDTO(
        @Min(value = 2024, message = "년도의 최소값은 2024 입니다 ")
        // TODO : @CurrnetYear : 현재 년독 최대값이므로 이에 대해 커스텀 유효성 어노테이션 정의 필요
        int year,

        @Min(value = 1, message = "월은 1월부터 시작입니다")
        @Max(value = 12, message = "월은 12월까지 입니다 ")
        int month,

        @Min(value = 1, message = "일은 1일부터 시작입니다")
        // TODO : @Maxday : 각 월에 맞는 최대 일수를 검사하는 커스텀 어노테이션 정의 필요
        int day
) {
    public LocalDate toLocalDate() {
        return LocalDate.of(year, month, day);
    }
}
