package homeTry.diary.model.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

import homeTry.diary.model.vo.Memo;
import homeTry.member.model.entity.Member;

@Entity
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "memo", nullable = false))
    private Memo memo; 

    @ManyToOne
    @JoinColumn(name = "member_email", referencedColumnName = "email", nullable = false)
    private Member member;

    protected Diary() {}

    public Diary(LocalDateTime createdAt, String memo, Member member) {
        this.createdAt = createdAt;
        this.memo = new Memo(memo);
        this.member = member;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreateAt() {
        return createdAt;
    }

    public Memo getMemo() {
        return memo;
    }

    public Member getMember() {
        return member;
    }
}
