package homeTry.interceptor;

import homeTry.auth.JwtAuth;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    private final JwtAuth jwtAuth;

    public JwtInterceptor(JwtAuth jwtAuth) {
        this.jwtAuth = jwtAuth;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {
        String authHeader = request.getHeader("Authorization");

/*        if ("GET".equalsIgnoreCase(request.getMethod()) &&
                (request.getRequestURI().matches("/api/products(/\\d+)?"))) {
            return true;
        }*/

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtAuth.validateToken(token)) {
                return true;
            }
        }

        response.addHeader("WWW-Authenticate", "Bearer");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        return false;
    }

}
