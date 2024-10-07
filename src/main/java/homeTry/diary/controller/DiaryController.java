package homeTry.diary.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import homeTry.annotation.LoginMember;
import homeTry.diary.dto.request.DiaryRequest;
import homeTry.diary.service.DiaryService;
import homeTry.member.dto.MemberDTO;

@RestController
@RequestMapping("/api/diary")
public class DiaryController {

    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @PostMapping
    public ResponseEntity<Void> createDiary(
        @RequestBody DiaryRequest diaryRequest,
        @LoginMember MemberDTO memberDTO
    ) {

        diaryService.createDiary(diaryRequest, memberDTO.id());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{diaryId}")
    public ResponseEntity<Void> deleteDiary(
        @PathVariable Long diaryId,
        @LoginMember MemberDTO memberDto
    ) {

        diaryService.deleteDiary(diaryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
