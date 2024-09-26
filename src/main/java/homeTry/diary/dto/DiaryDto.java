package homeTry.diary.dto;

import java.time.LocalDate;

public record DiaryDto(
    Long id,
    LocalDate createAt,
    String memo,
    String email
) {
}
