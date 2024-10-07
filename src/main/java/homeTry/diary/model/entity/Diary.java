package homeTry.diary.model.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import homeTry.diary.model.vo.Memo;
import homeTry.member.model.entity.Member;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Diary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "memo", nullable = false))
    private Memo memo;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "member_id", nullable = false)
    private Member member;

    protected Diary() {
    }

    public Diary(String memo, Member member) {
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
