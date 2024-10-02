package homeTry.team.dto;

import homeTry.team.model.entity.Team;

public record ResponseTotalTeam (
        String teamName,
        String teamDescription,
        long maxParticipants,
        long currentParticipants,
        String password
){
    public static ResponseTotalTeam of(Team team){
        return new ResponseTotalTeam(team.getTeamName().getValue(),
                team.getTeamDescription().getValue(),
                team.getMaxParticipants().getValue(),
                team.getCurrentParticipants().getValue(),
                team.getPassword().getValue());
    }
}
