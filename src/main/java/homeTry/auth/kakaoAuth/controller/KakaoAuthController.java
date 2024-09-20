package homeTry.auth.kakaoAuth.controller;

import homeTry.auth.JwtAuth;
import homeTry.auth.kakaoAuth.service.KakaoAuthService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/oauth/login")
public class KakaoAuthController {
    private final KakaoAuthService kakaoAuthService;
    private final JwtAuth jwtAuth;

    @Autowired
    KakaoAuthController(KakaoAuthService kakaoAuthService, JwtAuth jwtAuth) {
        this.kakaoAuthService = kakaoAuthService;
        this.jwtAuth = jwtAuth;
    }

    @Hidden
    @GetMapping
    public String loginOrRegister(@RequestParam String code) {
        return jwtAuth.generateToken(kakaoAuthService.loginOrRegister(code));
    }
}
