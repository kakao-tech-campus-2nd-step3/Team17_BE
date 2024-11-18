package homeTry.team.dto.response;

import homeTry.tag.teamTag.dto.AllTeamTagDTO;
import homeTry.tag.teamTag.dto.TeamTagDTO;

import java.util.List;

public record TagListResponse(
        List<TeamTagDTO> genderTagList,
        List<TeamTagDTO> ageTagList,
        List<TeamTagDTO> exerciseIntensityTagList
) {
    public static TagListResponse of(AllTeamTagDTO allTeamTagDTO) {
        return new TagListResponse(
                allTeamTagDTO.genderTagList(),
                allTeamTagDTO.ageTagList(),
                allTeamTagDTO.exerciseIntensityTagList());
    }
}