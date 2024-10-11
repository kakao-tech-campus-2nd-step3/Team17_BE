package homeTry.common.auth.kakaoAuth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "kakao")
public record KakaoAuthConfig(
        String restApiKey,
        String redirectUri,
        String tokenUrl,
        String userInfoUrl
) { }