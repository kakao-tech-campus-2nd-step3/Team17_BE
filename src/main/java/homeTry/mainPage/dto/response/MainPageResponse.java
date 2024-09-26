package homeTry.mainPage.dto.response;

import java.time.Duration;

import homeTry.diary.dto.DiaryDto;

public record MainPageResponse(
    Duration totaltime,
    // List<ExerciseDto> exerciseList,
    DiaryDto diary
) {}
