package homeTry.mainPage.service;

import homeTry.diary.service.DiaryService;
import homeTry.exerciseList.service.ExerciseHistoryService;
import homeTry.exerciseList.service.ExerciseTimeService;
import homeTry.mainPage.dto.request.MainPageRequest;
import homeTry.mainPage.dto.response.MainPageResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

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

    @Transactional(readOnly = true)
    public MainPageResponse getMainPage(MainPageRequest mainPageRequest, Long memberId) {

        LocalDate date = mainPageRequest.toDate();

        if (mainPageRequest.isToday(date)) {
            return getTodayMainPageResponse(memberId);
        }

        return getHistoricalMainPageResponse(memberId, date);

    }

    private MainPageResponse getTodayMainPageResponse(Long memberId) {

        return new MainPageResponse(
                exerciseTimeService.getExerciseTimesForToday(memberId),
                exerciseTimeService.getExerciseResponsesForToday(memberId),
                diaryService.getDiaryByDate(LocalDate.now(), memberId));

    }

    private MainPageResponse getHistoricalMainPageResponse(Long memberId, LocalDate date) {

        return new MainPageResponse(
                exerciseHistoryService.getExerciseHistoriesForDay(memberId, date),
                exerciseHistoryService.getExerciseResponsesForDay(memberId, date),
                diaryService.getDiaryByDate(date, memberId));
    }
}
