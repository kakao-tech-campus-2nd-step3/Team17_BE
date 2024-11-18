package homeTry.diary.model.entity;

import homeTry.common.entity.BaseEntity;
import homeTry.diary.model.vo.Memo;
import homeTry.member.model.entity.Member;
import jakarta.persistence.*;

@Entity
@Table(indexes = {
        @Index(name = "idx_member_created_at", columnList = "member_id, created_at")
    }
)
public class Diary extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "memo", nullable = false))
    private Memo memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
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

    public Memo getMemo() {
        return memo;
    }

    public Member getMember() {
        return member;
    }
}
