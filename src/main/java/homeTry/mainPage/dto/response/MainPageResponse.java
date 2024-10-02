package homeTry.mainPage.dto.response;

import java.time.Duration;
import java.util.List;

import homeTry.diary.dto.DiaryDto;
import homeTry.exerciseList.dto.ExerciseResponse;

public record MainPageResponse(
    Duration totaltime,
    List<ExerciseResponse> exerciseList, 
    List<DiaryDto> diaries
) {}
