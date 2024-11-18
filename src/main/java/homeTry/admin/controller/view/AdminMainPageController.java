package homeTry.admin.controller.view;

import homeTry.common.auth.kakaoAuth.config.KakaoAuthConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "Admin Main Page View", description = "관리자 메인 페이지 SSR View 엔트포인트")
@Controller
public class AdminMainPageController {

    private final KakaoAuthConfig kakaoAuthConfig;

    public AdminMainPageController(KakaoAuthConfig kakaoAuthConfig) {
        this.kakaoAuthConfig = kakaoAuthConfig;
    }

    @GetMapping
    @Operation(summary = "관리자 메인 페이지", description = "관리자 메인 페이지를 html로 응답한다.")
    @ApiResponse(responseCode = "200", description = "관리자 메인 페이지를 성공적으로 응답함", content = @Content(mediaType = "text/html"))
    public String getAdminMainPage(Model model) {
        String kakaoAuthUrl =
                "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id="
                        + kakaoAuthConfig.restApiKey() + "&redirect_uri="
                        + kakaoAuthConfig.redirectUri();

        model.addAttribute("kakaoAuthUrl", kakaoAuthUrl);

        return "admin/adminMain";
    }
}
