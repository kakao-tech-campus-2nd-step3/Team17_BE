package homeTry.diary.model.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import homeTry.diary.model.vo.Memo;

@Entity
@Table(name = "diary")
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime createAt;

    @Embedded
    @Column(nullable = false)
    private Memo memo;

    @Column(nullable = false)
    private String memberEmail;

    public Diary(LocalDateTime createAt, Memo memo, String memberEmail) {
        this.createAt = createAt;
        this.memo = memo;
        this.memberEmail = memberEmail;
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

    public String getMemberEmail() {
        return memberEmail;
    }
}
