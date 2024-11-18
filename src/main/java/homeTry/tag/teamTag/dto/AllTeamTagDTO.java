package homeTry.tag.teamTag.dto;

import java.util.List;

public record AllTeamTagDTO(
        List<TeamTagDTO> genderTagList,
        List<TeamTagDTO> ageTagList,
        List<TeamTagDTO> exerciseIntensityTagList
) {
}
