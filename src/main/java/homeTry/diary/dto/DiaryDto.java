package homeTry.diary.dto;

import java.time.LocalDateTime;

public record DiaryDto(
    Long id,
    LocalDateTime createAt,
    String memo,
    String email
) {
}
