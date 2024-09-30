package homeTry.diary.dto;

import java.time.LocalDateTime;

import homeTry.diary.model.entity.Diary;

public record DiaryDto(
    Long id,
    LocalDateTime createAt,
    String memo,
    String email
) {
    public static DiaryDto convertToDiaryDto(Diary diary) {
        return new DiaryDto(diary.getId(), diary.getCreateAt(), diary.getMemo().value(), diary.getMember().getEmail());
    }
}
