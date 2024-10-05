package homeTry.mainPage.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

import homeTry.diary.service.DiaryService;
import homeTry.exerciseList.service.ExerciseHistoryService;
import homeTry.exerciseList.service.ExerciseTimeService;
import homeTry.mainPage.dto.request.MainPageRequest;
import homeTry.mainPage.dto.response.MainPageResponse;

@Service
public class MainPageService {

    private final DiaryService diaryService;
    private final ExerciseTimeService exerciseTimeService;
    private final ExerciseHistoryService exerciseHistoryService;

    public MainPageService(DiaryService diaryService, 
                            ExerciseTimeService exerciseTimeService, 
                            ExerciseHistoryService exerciseHistoryService) {
        this.diaryService = diaryService;
        this.exerciseTimeService = exerciseTimeService;
        this.exerciseHistoryService = exerciseHistoryService;
    }
    
    public MainPageResponse getMainPage(MainPageRequest mainPageRequest, Long memberId) {

        LocalDate date = LocalDate.of(mainPageRequest.year(), mainPageRequest.month(), mainPageRequest.day());

        if(isToday(date)) {

            return new MainPageResponse(
                exerciseTimeService.getExerciseTimesForToday(memberId), 
                exerciseTimeService.getExerciseResponsesForToday(memberId),
                diaryService.getDiaryByDate(date, memberId));

        } else {

            return new MainPageResponse(
                exerciseHistoryService.getExerciseHistoriesForDay(memberId, date), 
                exerciseHistoryService.getExerciseResponsesForDay(memberId, date),
                diaryService.getDiaryByDate(date, memberId));
        }
    }

    private boolean isToday(LocalDate day){
        return day.equals(LocalDate.now());
    }
}
