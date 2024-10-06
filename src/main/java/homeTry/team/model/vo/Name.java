package homeTry.team.model.vo;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public record Name (String value) {
    public Name {
        validateName(value);
    }

    private void validateName(String value) {
        if(value == null || value.isBlank())
            throw new IllegalArgumentException("이름값은 필수입니다");
    }
}
