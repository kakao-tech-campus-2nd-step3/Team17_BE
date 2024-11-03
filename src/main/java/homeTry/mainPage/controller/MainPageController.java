package homeTry.mainPage.controller;

import homeTry.common.annotation.DateValid;
import homeTry.common.annotation.LoginMember;
import homeTry.mainPage.dto.response.MainPageResponse;
import homeTry.mainPage.service.MainPageService;
import homeTry.member.dto.MemberDTO;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class MainPageController {

    private final MainPageService mainPageService;

    public MainPageController(MainPageService mainPageService) {
        this.mainPageService = mainPageService;
    }

    @GetMapping
    public ResponseEntity<MainPageResponse> mainPage(
            @RequestParam(name = "date") @DateValid @DateTimeFormat(pattern = "yyyyMMdd") LocalDate date,
            @LoginMember MemberDTO memberDTO) {

        return new ResponseEntity<>(mainPageService.getMainPage(date, memberDTO.id()),
                HttpStatus.OK);
    }
}
