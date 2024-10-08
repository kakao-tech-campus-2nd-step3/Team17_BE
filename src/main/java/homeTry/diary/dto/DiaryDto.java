package homeTry.diary.dto;

import java.time.LocalDateTime;

import homeTry.diary.model.entity.Diary;

public record DiaryDto(
    Long id,
    LocalDateTime createdAt,
    String memo) {

    public static DiaryDto from(Diary diary) {
        return new DiaryDto(diary.getId(), diary.getCreatedAt(), diary.getMemo().value());
    }
}
