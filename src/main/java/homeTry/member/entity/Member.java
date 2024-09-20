package homeTry.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

@Entity
public class Member {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@(.+)$"
    );

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    @Size(max = 255)
    private String email;

    @Size(max = 255)
    @Column(nullable = false)
    private String password;

    @Size(max = 255)
    @Column(nullable = false)
    private String nickname;

    @Column(nullable = true)
    private String accessToken;

    @Column(nullable = false)
    private LocalDateTime registeredAt;

    protected Member() { }

    public Member(String email, String password, String nickname, LocalDateTime registeredAt) {
        validateEmail(email);
        validatePassword(password);

        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return password;}
    public String getNickname() { return nickname; }
    public String getAccessToken() { return accessToken;}
    public LocalDateTime getRegisteredAt() { return registeredAt; }

    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }


    private void validateEmail(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("올바른 이메일 형식이 아닙니다.");
        }
    }

    private void validatePassword(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("비밀번호를 입력해주세요.");
        }
    }
}