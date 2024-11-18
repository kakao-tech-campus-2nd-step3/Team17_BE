package homeTry.common.auth.jwt;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private final JwtAuth jwtAuth;

    public JwtUtil(JwtAuth jwtAuth) {  // 생성자 주입
        this.jwtAuth = jwtAuth;
    }

    public boolean isValidBearerToken(String bearerToken) {

        if (bearerToken == null || !bearerToken.startsWith("Bearer "))
            return false;

        return jwtAuth.validateToken(bearerToken.substring(7));
    }
}