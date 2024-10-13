package homeTry.mainPage.dto.response;

import homeTry.diary.dto.DiaryDto;
import homeTry.exerciseList.dto.response.ExerciseResponse;

import java.time.Duration;
import java.util.List;

public record MainPageResponse(
        Duration totalTime,
        List<ExerciseResponse> exerciseList,
        List<DiaryDto> diaries) {

}
