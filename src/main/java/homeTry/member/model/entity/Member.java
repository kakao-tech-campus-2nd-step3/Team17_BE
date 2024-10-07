package homeTry.member.model.entity;

import homeTry.member.model.vo.Email;
import homeTry.member.model.vo.Nickname;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "email", nullable = false))
    private Email email;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "nickname", nullable = false))
    private Nickname nickname;

    @Column(nullable = true)
    private String kakaoAccessToken;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime registrationDate;

    protected Member() {
    }

    public Member(String email, String nickname) {
        this.nickname = new Nickname(nickname);
        this.email = new Email(email);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email.value();
    }

    public String getNickname() {
        return nickname.value();
    }

    public String getKakaoAccessToken() {
        return kakaoAccessToken;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setKakaoAccessToken(String kakaoAccessToken) {
        this.kakaoAccessToken = kakaoAccessToken;
    }

    public void changeNickname(Nickname nickname) {
        this.nickname = nickname;
    }


}