package homeTry.common.auth.kakaoAuth.controller.rest;

import homeTry.common.auth.jwt.JwtAuth;
import homeTry.common.auth.kakaoAuth.service.KakaoAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Kakao Auth Rest", description = "카카오 인증 관련 Rest API")
@RestController
@RequestMapping("/api/oauth/login")
public class KakaoAuthRestController {

    private final KakaoAuthService kakaoAuthService;
    private final JwtAuth jwtAuth;

    @Autowired
    KakaoAuthRestController(KakaoAuthService kakaoAuthService, JwtAuth jwtAuth) {
        this.kakaoAuthService = kakaoAuthService;
        this.jwtAuth = jwtAuth;
    }

    @GetMapping
    @Operation(summary = "토큰 발급하기", description = "Kakao가 준 인가코드로 토큰을 발급하여 응답한다.")
    @ApiResponse(responseCode = "200", description = "토큰을 성공적으로 발급함")
    public String loginOrRegister(@RequestParam(name = "code") String code) {
        return jwtAuth.generateToken(kakaoAuthService.loginOrRegister(code));
    }
}
