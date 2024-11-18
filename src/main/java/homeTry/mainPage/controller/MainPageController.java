package homeTry.mainPage.controller;

import homeTry.common.annotation.DateValid;
import homeTry.common.annotation.LoginMember;
import homeTry.mainPage.dto.response.MainPageResponse;
import homeTry.mainPage.service.MainPageService;
import homeTry.member.dto.MemberDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Tag(name = "Mainpage", description = "메인페이지 관련 API")
@RestController
@RequestMapping("/api")
public class MainPageController {

    private final MainPageService mainPageService;

    public MainPageController(MainPageService mainPageService) {
        this.mainPageService = mainPageService;
    }

    @GetMapping
    @Operation(summary = "메인 페이지 조회", description = "date Parameter에 해당하는 메인페이지 조회")
    @ApiResponse(responseCode = "200", description = "메인페이지 조회 성공")
    public ResponseEntity<MainPageResponse> mainPage(
            @RequestParam(name = "date") @DateValid @DateTimeFormat(pattern = "yyyyMMdd") LocalDate date,
            @PageableDefault(size = 5, sort = "createdAt", direction = Sort.Direction.ASC) Pageable pageable,
            @LoginMember MemberDTO memberDTO) {

        return new ResponseEntity<>(mainPageService.getMainPage(date, memberDTO.id(), pageable),
                HttpStatus.OK);
    }
}
