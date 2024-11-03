package homeTry.team.model.entity;

import homeTry.common.entity.BaseEntity;
import homeTry.tag.teamTag.model.entity.TeamTag;
import jakarta.persistence.*;

@Entity
public class TeamTagMapping extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private TeamTag teamTag;

    protected TeamTagMapping() {
    }

    public TeamTagMapping(TeamTag teamTag, Team team) {
        this.teamTag = teamTag;
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public TeamTag getTeamTag() {
        return teamTag;
    }
}