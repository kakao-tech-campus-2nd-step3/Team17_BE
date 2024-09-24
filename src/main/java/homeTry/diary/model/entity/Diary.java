package homeTry.diary.model.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import homeTry.diary.model.vo.Memo;
import homeTry.member.model.vo.Email;

@Entity
@Table(name = "diary")
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime createAt;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "memo", nullable = false))
    private Memo memo; 

    @Column(nullable = false)
    private Email memberEmail;

    protected Diary() {}

    public Diary(LocalDateTime createAt, String memo, String memberEmail) {
        this.createAt = createAt;
        this.memo = new Memo(memo);
        this.memberEmail = new Email(memberEmail);
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public Memo getMemo() {
        return memo;
    }

    public Email getMemberEmail() {
        return memberEmail;
    }
}
