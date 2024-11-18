package homeTry.tag.teamTag.dto;

import homeTry.tag.teamTag.model.entity.TeamTag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TeamTagDTO(
        Long tagId,
        String teamTagName,
        String teamTagAttribute
) {

    public static TeamTagDTO from(TeamTag teamTag) {
        return new TeamTagDTO(
                teamTag.getId(),
                teamTag.getTagName().value(),
                teamTag.getTagAttribute().value());
    }
}