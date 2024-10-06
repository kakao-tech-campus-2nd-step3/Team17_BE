package homeTry.tag.dto;

import homeTry.tag.model.entity.Tag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TagDTO(
        @NotNull(message = "tagId는 필수입니다")
        @Positive(message = "tagId는 양수값이여야 합니다")
        Long tagId,
        String tagName,
        String tagAttribute
) {
    public static TagDTO of(Tag tag) {
        return new TagDTO(
                tag.getId(),
                tag.getTagName().value(),
                tag.getTagAttribute().value());
    }
}