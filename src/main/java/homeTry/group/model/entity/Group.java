package homeTry.group.model.entity;

import homeTry.group.model.vo.*;
import jakarta.persistence.*;

@Entity
@Table(name = "`group`")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "name", nullable = false, unique = true, length = 15))
    private Name groupName;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "group_description"))
    private Description groupDescription;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "chief_email", nullable = false))
    private Email chiefEmail;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "max_participants", nullable = false))
    private Participant maxParticipants;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "current_participants", nullable = false))
    private Participant currentParticipants;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "password", nullable = false))
    private Password password;

    protected Group() {
    }

    public Group(String groupName, String groupDescription, String chiefEmail, long maxParticipants, long currentParticipants, String password) {
        this.groupName = new Name(groupName);
        this.groupDescription = new Description(groupDescription);
        this.chiefEmail = new Email(chiefEmail);
        this.maxParticipants = new Participant(maxParticipants);
        this.currentParticipants = new Participant(currentParticipants);
        this.password = new Password(password);
    }

    public Long getId() {
        return id;
    }

    public Name getGroupName() {
        return groupName;
    }

    public Description getGroupDescription() {
        return groupDescription;
    }

    public Email getChiefEmail() {
        return chiefEmail;
    }

    public Participant getMaxParticipants() {
        return maxParticipants;
    }

    public Participant getCurrentParticipants() {
        return currentParticipants;
    }

    public Password getPassword() {
        return password;
    }

    public void updateGroup(String groupName, String groupDescription, String chiefEmail, long maxParticipants, long currentParticipants, String password) {
        this.groupName = new Name(groupName);
        this.groupDescription = new Description(groupDescription);
        this.chiefEmail = new Email(chiefEmail);
        this.maxParticipants = new Participant(maxParticipants);
        this.currentParticipants = new Participant(currentParticipants);
        this.password = new Password(password);
    }
}
