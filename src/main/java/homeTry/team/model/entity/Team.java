package homeTry.team.model.entity;

import homeTry.team.model.vo.*;
import jakarta.persistence.*;

@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "name", nullable = false, unique = true, length = 15))
    private Name teamName;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "team_description"))
    private Description teamDescription;

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

    protected Team() {
    }

    public Team(String teamName, String teamDescription, String chiefEmail, long maxParticipants, long currentParticipants, String password) {
        this.teamName = new Name(teamName);
        this.teamDescription = new Description(teamDescription);
        this.chiefEmail = new Email(chiefEmail);
        this.maxParticipants = new Participant(maxParticipants);
        this.currentParticipants = new Participant(currentParticipants);
        this.password = new Password(password);
    }

    public Long getId() {
        return id;
    }

    public Name getTeamName() {
        return teamName;
    }

    public Description getTeamDescription() {
        return teamDescription;
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

    public void updateTeam(String teamName, String teamDescription, String chiefEmail, long maxParticipants, long currentParticipants, String password) {
        this.teamName = new Name(teamName);
        this.teamDescription = new Description(teamDescription);
        this.chiefEmail = new Email(chiefEmail);
        this.maxParticipants = new Participant(maxParticipants);
        this.currentParticipants = new Participant(currentParticipants);
        this.password = new Password(password);
    }
}
