package homeTry.team.dto.response;

import homeTry.tag.teamTag.dto.TeamTagDTO;

import java.util.List;

public record NewTeamFromResponse(
        List<TeamTagDTO> tagList
) {

}
