package homeTry.diary.dto;

import homeTry.diary.model.entity.Diary;

import java.time.LocalDateTime;

public record DiaryDto(
        Long id,
        LocalDateTime createdAt,
        String memo) {

    public static DiaryDto from(Diary diary) {
        return new DiaryDto(diary.getId(), diary.getCreatedAt(), diary.getMemo().value());
    }
}
