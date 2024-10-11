package homeTry.common.auth.kakaoAuth.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties(KakaoAuthConfig.class)  // KakaoAuthConfig를 빈으로 등록
@PropertySource({"classpath:application-kakao-login.properties",
        "classpath:application-secret.properties"})
public class KakaoAuthConfigRegistrar { }