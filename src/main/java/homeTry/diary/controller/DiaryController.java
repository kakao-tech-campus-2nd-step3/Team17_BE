package homeTry.diary.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import homeTry.diary.dto.request.DiaryRequest;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/diary")
public class DiaryController {
    
    @PostMapping
    public ResponseEntity<Void> createDiary(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody DiaryRequest diaryRequest
            ) {
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{diaryId}")
    public ResponseEntity<Void> deleteDiary(
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable Long diaryId
    ) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
