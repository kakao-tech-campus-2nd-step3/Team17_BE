package homeTry.team.model.vo;

import jakarta.persistence.Embeddable;

@Embeddable
public record Name(String value) {

    public Name {
        validateName(value);
    }

    private void validateName(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("이름값은 필수입니다");
        }
        if (value.length() > 15) {
            throw new IllegalArgumentException("이름의 길이는 최대 15자 입니다");
        }
    }
}
