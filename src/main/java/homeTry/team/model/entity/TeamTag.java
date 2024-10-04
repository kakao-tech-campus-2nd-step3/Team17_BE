package homeTry.team.model.entity;

import homeTry.tag.model.entity.Tag;
import jakarta.persistence.*;

@Entity
public class TeamTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    Tag tag;

    protected TeamTag () {}

    public TeamTag(Tag tag, Team team) {
        this.tag = tag;
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public Tag getTag() {
        return tag;
    }
}