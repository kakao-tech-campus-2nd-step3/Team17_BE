package homeTry.tag.teamTag.model.vo;

import jakarta.persistence.Embeddable;

@Embeddable
public record TeamTagAttribute(String value) {

    public TeamTagAttribute {
        validateTeamTagAttribute(value);
    }

    private void validateTeamTagAttribute(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("팀태그 속성은 필수입니다");
        }
        if (value.length() > 15) {
            throw new IllegalArgumentException("팀태그 속성값은 최대 15자 입니다");
        }
    }
}
