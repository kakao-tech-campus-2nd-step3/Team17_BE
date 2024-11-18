package homeTry.team.dto.response;

import homeTry.tag.teamTag.dto.TeamTagDTO;
import homeTry.team.model.entity.Team;

import java.util.List;

public record TeamResponse(
        Long id,
        String teamName,
        String leaderNickname,
        String teamDescription,
        long maxParticipants,
        long currentParticipants,
        boolean hasPassword,
        List<TeamTagDTO> tagList
) {


    public static TeamResponse of(Team team, List<TeamTagDTO> tagList, String leaderNickname) {
        boolean hasPassword = team.getPassword().isPresent();

        return new TeamResponse(
                team.getId(),
                team.getTeamName().value(),
                leaderNickname,
                team.getTeamDescription().value(),
                team.getMaxParticipants().value(),
                team.getCurrentParticipants().value(),
                hasPassword,
                tagList);
    }
}