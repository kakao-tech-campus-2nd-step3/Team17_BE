package homeTry.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;

@Configuration
@OpenAPIDefinition(
    info = @Info(title = "home-try API",
        description = "카카오테크 캠퍼스 Step3 17조, 홈트라이의 API 명세서",
        version = "v1"),
    security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    scheme = "bearer",
    bearerFormat = "JWT"
)
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi kakaoApi() {
        return GroupedOpenApi.builder()
            .group("Kakao Auth")
            .pathsToMatch("/api/oauth/login", "/oauth/login/ssr")
            .build();
    }

    @Bean
    public GroupedOpenApi userApi() {
        return GroupedOpenApi.builder()
            .group("User Services")
            .pathsToMatch("/api/**")
            .pathsToExclude("/api/oauth/login")
            .build();
    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
            .group("Admin") // 관리자 관련 그룹 이름 정의
            .pathsToMatch("/admin/**", "/") // 그룹화할 관리자 관련 API 경로들 정의
            .build();
    }


}
