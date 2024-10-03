package homeTry.team.dto;

import homeTry.team.model.entity.Team;

public record ResponseTeam(
        String teamName,
        String leaderNickname,
        String teamDescription,
        long maxParticipants,
        long currentParticipants,
        String password
){
    public static ResponseTeam of(Team team){
        return new ResponseTeam(team.getTeamName().getValue(),
                team.getLeader().getNickname(),
                team.getTeamDescription().getValue(),
                team.getMaxParticipants().getValue(),
                team.getCurrentParticipants().getValue(),
                team.getPassword().getValue());
    }
}