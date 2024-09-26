package homeTry.diary.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import homeTry.diary.dto.request.DiaryRequest;
import homeTry.diary.service.DiaryService;

@RestController
@RequestMapping("/api/diary")
public class DiaryController {

    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }
    
    @PostMapping
    public ResponseEntity<Void> createDiary(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody DiaryRequest diaryRequest
    ) {
        
        diaryService.createDiary(diaryRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{diaryId}")
    public ResponseEntity<Void> deleteDiary(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long diaryId
    ) {

        diaryService.deleteDiary(diaryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
