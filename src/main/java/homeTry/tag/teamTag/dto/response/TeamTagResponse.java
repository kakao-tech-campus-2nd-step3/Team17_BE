package homeTry.tag.teamTag.dto.response;

import homeTry.tag.teamTag.dto.TeamTagDTO;

import java.util.List;


public record TeamTagResponse(
    List<TeamTagDTO> teamTags) {
}
