package homeTry.diary.controller;

import homeTry.common.annotation.LoginMember;
import homeTry.diary.dto.request.DiaryRequest;
import homeTry.diary.service.DiaryService;
import homeTry.member.dto.MemberDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            @LoginMember MemberDTO memberDTO) {

        diaryService.createDiary(diaryRequest, memberDTO.id());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{diaryId}")
    public ResponseEntity<Void> deleteDiary(
            @PathVariable Long diaryId,
            @LoginMember MemberDTO memberDto) {

        diaryService.deleteDiary(diaryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
