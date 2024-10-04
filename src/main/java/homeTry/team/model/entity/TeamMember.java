package homeTry.team.model.entity;

import homeTry.member.model.entity.Member;
import jakarta.persistence.*;

@Entity
public class TeamMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Member member;

    protected TeamMember () {}

    public TeamMember(Member member, Team team) {
        this.member = member;
        this.team = team;
    }

    public Member getMember() {
        return member;
    }

    public Team getTeam() {
        return team;
    }
}
