package homeTry.member.model.entity;

import homeTry.common.entity.BaseEntity;
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
import jakarta.persistence.PrePersist;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Member extends BaseEntity {

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

    @Column(nullable = false)
    private Integer exerciseAttendanceDate;

    protected Member() {
    }

    public Member(String email, String nickname) {
        this.nickname = new Nickname(nickname);
        this.email = new Email(email);
    }

    @PrePersist // jpa가 DB에 insert 하기 직전에 실행되는 메서드
    public void prePersist() { // null 일때 default 값으로 0을 해줌
        this.exerciseAttendanceDate =
                (this.exerciseAttendanceDate == null) ? 0 : this.exerciseAttendanceDate;
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

    public Integer getExerciseAttendanceDate() {
        return exerciseAttendanceDate;
    }

    public void setKakaoAccessToken(String kakaoAccessToken) {
        this.kakaoAccessToken = kakaoAccessToken;
    }

    public void changeNickname(Nickname nickname) {
        this.nickname = nickname;
    }

    public void incrementAttendanceDate() {
        this.exerciseAttendanceDate++;
    }

}