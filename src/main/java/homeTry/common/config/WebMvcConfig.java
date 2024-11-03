package homeTry.common.config;

import homeTry.common.auth.JwtAuth;
import homeTry.common.auth.LoginMemberArgumentResolver;
import homeTry.common.interceptor.JwtInterceptor;
import homeTry.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final MemberService memberService;
    private final JwtInterceptor jwtInterceptor;
    private final JwtAuth jwtAuth;

    @Autowired
    WebMvcConfig(MemberService memberService, JwtInterceptor jwtInterceptor, JwtAuth jwtAuth) {
        this.memberService = memberService;
        this.jwtInterceptor = jwtInterceptor;
        this.jwtAuth = jwtAuth;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/api/oauth/**");
        //토큰 받는 경로 지정
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new LoginMemberArgumentResolver(memberService, jwtAuth));
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("http://localhost:3000", "http://localhost:8080", "http://localhost:63342", "https://localhost:3000")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*") 
            .exposedHeaders(HttpHeaders.LOCATION)
            .allowCredentials(true);  
    }

}
