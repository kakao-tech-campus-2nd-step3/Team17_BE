package homeTry.mainPage.service;

import org.springframework.stereotype.Service;

import java.time.Duration;
import homeTry.diary.dto.DiaryDto;
import homeTry.diary.service.DiaryService;
import homeTry.mainPage.dto.request.MainPageRequest;
import homeTry.mainPage.dto.response.MainPageResponse;

@Service
public class MainPageService {

    private final DiaryService diaryService;

    public MainPageService(DiaryService diaryService) {
        this.diaryService = diaryService;
    }
    
    public MainPageResponse getMainPage(MainPageRequest mainPageRequest, String email) {

        Duration totaltime = Duration.ZERO; //temp

        DiaryDto diary = diaryService.getDiaryByDate(mainPageRequest.year(), mainPageRequest.month(), mainPageRequest.day(), email);
        return new MainPageResponse(totaltime, diary);
    }
}
