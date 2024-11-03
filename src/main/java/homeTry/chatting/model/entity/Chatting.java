package homeTry.chatting.model.entity;

import homeTry.common.entity.BaseEntity;
import homeTry.team.model.entity.TeamMember;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Chatting extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private TeamMember teamMember;

    @Column(nullable = false)
    private String message;

    protected Chatting() { }

    public Chatting(TeamMember teamMember, String message) {
        this.teamMember = teamMember;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public TeamMember getTeamMember() {
        return teamMember;
    }

    public String getMessage() {
        return message;
    }
}
