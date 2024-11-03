package homeTry.mainPage.dto.response;

import homeTry.diary.dto.DiaryDto;
import homeTry.exerciseList.dto.response.ExerciseResponse;
import org.springframework.data.domain.Slice;

import java.util.List;

public record MainPageResponse(
        Long totalTime,
        List<ExerciseResponse> exerciseList,
        Slice<DiaryDto> diaries) {

}
