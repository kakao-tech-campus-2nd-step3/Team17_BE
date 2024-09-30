package homeTry.mainPage.dto.response;

import java.time.Duration;
import java.util.List;

import homeTry.diary.dto.DiaryDto;

public record MainPageResponse(
    Duration totaltime,
    // List<ExerciseDto> exerciseList,
    List<DiaryDto> diaries
) {}
