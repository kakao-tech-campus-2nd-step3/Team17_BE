package homeTry.team.dto.response;

import homeTry.tag.dto.TagDTO;

import java.util.List;

public record NewTeamFromResponse(
        List<TagDTO> tagList
) {

}
