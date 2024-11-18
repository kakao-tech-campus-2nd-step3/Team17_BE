package homeTry.common.auth.kakaoAuth.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties(KakaoAuthConfig.class)  // KakaoAuthConfig를 빈으로 등록
@Profile("prod") //배포 환경일 때 작동
@PropertySource({"classpath:application-prod-kakao-login.properties",
        "classpath:application-secret.properties"})
public class ProdKakaoAuthConfigRegistrar { }
