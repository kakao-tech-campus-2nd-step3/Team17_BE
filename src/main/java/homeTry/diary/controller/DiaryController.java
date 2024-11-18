package homeTry.diary.controller;

import homeTry.common.annotation.LoginMember;
import homeTry.diary.dto.request.DiaryRequest;
import homeTry.diary.service.DiaryService;
import homeTry.member.dto.MemberDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Diary", description = "일기 관련 API")
@RestController
@RequestMapping("/api/diary")
public class DiaryController {

    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @PostMapping
    @Operation(summary = "일기 생성", description = "diaryRequest의 memo로 운동 일기 생성")
    @ApiResponse(responseCode = "201", description = "일기 생성 성공")
    public ResponseEntity<Void> createDiary(
            @Valid @RequestBody DiaryRequest diaryRequest,
            @LoginMember MemberDTO memberDTO) {

        diaryService.createDiary(diaryRequest, memberDTO.id());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{diaryId}")
    @Operation(summary = "일기 삭제", description = "diaryId로 운동 일기 삭제")
    @ApiResponse(responseCode = "204", description = "일기 삭제 성공")
    public ResponseEntity<Void> deleteDiary(
            @PathVariable Long diaryId,
            @LoginMember MemberDTO memberDto) {

        diaryService.deleteDiary(diaryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
