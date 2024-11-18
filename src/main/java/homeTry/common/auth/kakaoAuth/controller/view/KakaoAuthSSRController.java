package homeTry.common.auth.kakaoAuth.controller.view;

import homeTry.common.auth.jwt.JwtAuth;
import homeTry.common.auth.kakaoAuth.service.KakaoAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Kakao Auth SSR", description = "카카오 인증 관련 SSR View 엔트포인트")
@Controller
@RequestMapping("/oauth/login/ssr")
public class KakaoAuthSSRController {

    private final KakaoAuthService kakaoAuthService;
    private final JwtAuth jwtAuth;

    @Autowired
    KakaoAuthSSRController(KakaoAuthService kakaoAuthService, JwtAuth jwtAuth) {
        this.kakaoAuthService = kakaoAuthService;
        this.jwtAuth = jwtAuth;
    }

    @GetMapping
    @Operation(summary = "토큰 발급하기", description = "Kakao가 준 인가코드로 토큰을 발급하여 html로 응답한다.")
    @ApiResponse(responseCode = "200", description = "토큰을 성공적으로 발급함", content = @Content(mediaType = "text/html"))
    public String loginOrRegisterSSR(@RequestParam(name = "code") String code, Model model) {
        String token = jwtAuth.generateToken(kakaoAuthService.loginOrRegister(code));
        model.addAttribute("access_token", token);
        return "admin/adminKakaoLoginResult";
    }
}
