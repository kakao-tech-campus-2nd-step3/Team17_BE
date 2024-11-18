package homeTry.chatting.model.entity;

import homeTry.chatting.model.vo.Message;
import homeTry.common.entity.BaseEntity;
import homeTry.team.model.entity.TeamMemberMapping;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Chatting extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private TeamMemberMapping teamMemberMapping;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "message", nullable = false, length = 511))
    private Message message;

    protected Chatting() {
    }

    public Chatting(TeamMemberMapping teamMember, String message) {
        this.teamMemberMapping = teamMember;
        this.message = new Message(message);
    }

    public Long getId() {
        return id;
    }

    public TeamMemberMapping getTeamMemberMapping() {
        return teamMemberMapping;
    }

    public String getMessage() {
        return message.value();
    }
}
