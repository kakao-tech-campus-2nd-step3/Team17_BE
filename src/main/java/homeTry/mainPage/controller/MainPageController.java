package homeTry.mainPage.controller;

import homeTry.common.annotation.LoginMember;
import homeTry.mainPage.dto.request.MainPageRequest;
import homeTry.mainPage.dto.response.MainPageResponse;
import homeTry.mainPage.service.MainPageService;
import homeTry.member.dto.MemberDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MainPageController {

    private final MainPageService mainPageService;

    public MainPageController(MainPageService mainPageService) {
        this.mainPageService = mainPageService;
    }

    @GetMapping
    public ResponseEntity<MainPageResponse> mainPage(
            @ModelAttribute MainPageRequest mainPageRequest,
            @LoginMember MemberDTO memberDTO) {

        return new ResponseEntity<>(mainPageService.getMainPage(mainPageRequest, memberDTO.id()),
                HttpStatus.OK);
    }
}
