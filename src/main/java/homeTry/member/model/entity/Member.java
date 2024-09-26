package homeTry.member.model.entity;

import homeTry.member.model.vo.Email;
import homeTry.member.model.vo.Nickname;
import homeTry.member.model.vo.Password;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Member {
    @Id
    @Column(nullable = false, unique = true)
    private Email email;

    @Embedded
    @Column(nullable = false)
    private Password password;

    @Embedded
    @Column(nullable = false)
    private Nickname nickname;

    @Column(nullable = true)
    private String kakaoAccessToken;

    @Column(nullable = false)
    private LocalDateTime registrationDate;

    protected Member() { }

    public Member(String email, String password, String nickname, LocalDateTime registrationDate) {
        this.nickname = new Nickname(nickname);
        this.email = new Email(email);
        this.password = new Password(password);
        this.registrationDate = registrationDate;
    }

    public String getEmail() { return email.email(); }
    public String getPassword() { return password.password(); }
    public String getNickname() { return nickname.nickname(); }
    public String getKakaoAccessToken() { return kakaoAccessToken;}
    public LocalDateTime getRegistrationDate() {return registrationDate;}

    public void setKakaoAccessToken(String kakaoAccessToken) { this.kakaoAccessToken = kakaoAccessToken; }
    public void changeNickname(Nickname nickname) { this.nickname = nickname; }
}