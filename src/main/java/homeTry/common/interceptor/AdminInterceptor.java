package homeTry.common.interceptor;

import homeTry.common.auth.jwt.JwtAuth;
import homeTry.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    private final MemberService memberService;
    private final JwtAuth jwtAuth;

    public AdminInterceptor(MemberService memberService, JwtAuth jwtAuth) {
        this.memberService = memberService;
        this.jwtAuth = jwtAuth;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String confirmedToken = request.getHeader("Authorization").substring(7);

        if(isValidAdminToken(confirmedToken))
            return true;

        // 유효하지 않은 관리자 토큰
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden");
        return false;
    }

    private boolean isValidAdminToken(String confirmedToken) {
        return memberService.isAdmin(jwtAuth.extractId(confirmedToken));
    }
}