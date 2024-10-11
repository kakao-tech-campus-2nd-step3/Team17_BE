package homeTry.team.dto;

import homeTry.tag.dto.TagDTO;
import homeTry.team.model.entity.Team;
import homeTry.team.model.vo.Password;

import java.util.List;

public record TeamResponse(
        Long id,
        String teamName,
        String leaderNickname,
        String teamDescription,
        long maxParticipants,
        long currentParticipants,
        String password,
        List<TagDTO> tagList
) {


    public static TeamResponse of(Team team, List<TagDTO> tagList) {
        String password = team.getPassword()
                .map(Password::getValue)
                .orElse(null);

        return new TeamResponse(
                team.getId(),
                team.getTeamName().value(),
                team.getLeader().getNickname(),
                team.getTeamDescription().value(),
                team.getMaxParticipants().value(),
                team.getCurrentParticipants().value(),
                password,
                tagList);
    }
}