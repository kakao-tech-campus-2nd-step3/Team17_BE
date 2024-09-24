package homeTry.auth;

import homeTry.member.dto.MemberDTO;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application-secret.properties")
public class JwtAuth {

    @Value("${jwt-secret-key}")
    private String secret;
    private JwtParser jwtParser;

    @PostConstruct
    public void init() {
        jwtParser = Jwts.parser().setSigningKey(secret);
    }

    public String extractEmail(String token) {
        return jwtParser
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String generateToken(MemberDTO memberDTO) {
        return Jwts.builder()
                .setSubject(memberDTO.email())
                .claim("nickname", memberDTO.nickname())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 21000))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Boolean validateToken(String token) {
        try {
            jwtParser.parseClaimsJws(token); // 서명 검증
        } catch (JwtException e) {
            return false;
        }
        return true;
    }


    private Boolean isTokenExpired(String token) {
        return jwtParser
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }
}

