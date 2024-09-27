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
    @AttributeOverride(name = "value", column = @Column(name = "team_description", nullable = false))
    private Description teamDescription;

    @Column( nullable = false)
    private Long leaderId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "max_participants", nullable = false))
    private Participant maxParticipants;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "current_participants", nullable = false))
    private Participant currentParticipants;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "password"))
    private Password password;

    protected Team() {
    }

    public Team(String teamName, String teamDescription, long leaderId, long maxParticipants, long currentParticipants, String password) {
        this.teamName = new Name(teamName);
        this.teamDescription = new Description(teamDescription);
        this.leaderId= leaderId;
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

    public Long getLeaderId () {
        return leaderId;
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

    public void updateTeam(String teamName, String teamDescription, long leaderId, long maxParticipants, long currentParticipants, String password) {
        this.teamName = new Name(teamName);
        this.teamDescription = new Description(teamDescription);
        this.leaderId = leaderId;
        this.maxParticipants = new Participant(maxParticipants);
        this.currentParticipants = new Participant(currentParticipants);
        this.password = new Password(password);
    }
}
