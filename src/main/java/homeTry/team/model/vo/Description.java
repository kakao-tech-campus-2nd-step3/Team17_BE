package homeTry.team.model.vo;

import jakarta.persistence.Embeddable;

@Embeddable
public record Description(String value) {
    private static final int MAX_LENGTH = 255;

    public Description {
        validateDescription(value);
    }

    private void validateDescription(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("description은 빈 공백이면 안됩니다.");
        }

        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("description은 최대 255자 입니다");
        }
    }
}
