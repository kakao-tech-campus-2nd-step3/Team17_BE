package homeTry.team.model.vo;

import jakarta.persistence.Embeddable;

@Embeddable
public record Description(String value) {

    public Description {
        validateDescription(value);
    }

    private void validateDescription(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("description은 빈 공백이면 안됩니다.");
        }
    }
}
