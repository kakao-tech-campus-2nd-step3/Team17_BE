package homeTry.common.auth.kakaoAuth.controller;

import homeTry.common.auth.JwtAuth;
import homeTry.common.auth.kakaoAuth.service.KakaoAuthService;
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

    @GetMapping
    public String loginOrRegister(@RequestParam(name = "code") String code) {
        return jwtAuth.generateToken(kakaoAuthService.loginOrRegister(code));
    }
}
