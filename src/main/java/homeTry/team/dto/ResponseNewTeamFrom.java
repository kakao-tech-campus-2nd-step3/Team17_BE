package homeTry.team.dto;

import homeTry.tag.dto.TagDTO;

import java.util.List;

public record ResponseNewTeamFrom (
        List<TagDTO> tagList
){
}
