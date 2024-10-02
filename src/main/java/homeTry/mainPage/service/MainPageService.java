package homeTry.mainPage.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

import homeTry.diary.service.DiaryService;
import homeTry.exerciseList.service.ExerciseHistoryService;
import homeTry.exerciseList.service.ExerciseService;
import homeTry.exerciseList.service.ExerciseTimeService;
import homeTry.mainPage.dto.request.MainPageRequest;
import homeTry.mainPage.dto.response.MainPageResponse;

@Service
public class MainPageService {

    private final DiaryService diaryService;
    private final ExerciseTimeService exerciseTimeService;
    private final ExerciseService exerciseService;
    private final ExerciseHistoryService exerciseHistoryService;

    public MainPageService(DiaryService diaryService, 
                            ExerciseTimeService exerciseTimeService, 
                            ExerciseService exerciseService,
                            ExerciseHistoryService exerciseHistoryService) {
        this.diaryService = diaryService;
        this.exerciseTimeService = exerciseTimeService;
        this.exerciseService = exerciseService;
        this.exerciseHistoryService = exerciseHistoryService;
    }
    
    public MainPageResponse getMainPage(MainPageRequest mainPageRequest, Long memberId) {

        LocalDate day = LocalDate.of(mainPageRequest.year(), mainPageRequest.month(), mainPageRequest.day());

        if(isToday(day)) {

            return new MainPageResponse(
                exerciseTimeService.getExerciseTimesForToday(memberId), 
                exerciseService.getExercisesForMember(memberId), 
                diaryService.getDiaryByDate(mainPageRequest.year(), mainPageRequest.month(), mainPageRequest.day(), memberId));

        } else {

            return new MainPageResponse(
                exerciseHistoryService.getExerciseHistoriesForDay(memberId, day), 
                null, 
                diaryService.getDiaryByDate(mainPageRequest.year(), mainPageRequest.month(), mainPageRequest.day(), memberId));
        }
    }

    private boolean isToday(LocalDate day){
        return day.equals(LocalDate.now());
    }
}
