package homeTry.common.auth.jwt;

import homeTry.member.dto.MemberDTO;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@PropertySource("classpath:application-secret.properties")
public class JwtAuth {

    private final static Integer HOUR = 3600;
    private final static Integer EXPIRED_MILLIS = 1000 * 6 * HOUR;

    @Value("${jwt-secret-key}")
    private String secret;
    private JwtParser jwtParser;

    @PostConstruct
    public void init() {
        jwtParser = Jwts.parser().setSigningKey(secret);
    }

    public Long extractId(String token) {
        return Long.valueOf(jwtParser
                .parseClaimsJws(token)
                .getBody()
                .getSubject());
    }

    public String generateToken(MemberDTO memberDTO) {
        return Jwts.builder()
                .setSubject(memberDTO.id().toString())
                .claim("email", memberDTO.email())
                .claim("nickname", memberDTO.nickname())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRED_MILLIS))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Boolean validateToken(String token) {
        try {
            jwtParser.parseClaimsJws(token); // 서명 검증
            return !isTokenExpired(token); // 만료 여부 확인
        } catch (JwtException e) {
            return false;
        }
    }


    private Boolean isTokenExpired(String token) {
        return jwtParser
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }
}

