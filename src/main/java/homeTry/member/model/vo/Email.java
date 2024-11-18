package homeTry.member.model.vo;

import jakarta.persistence.Embeddable;

import java.util.regex.Pattern;

@Embeddable
public record Email(String value) {

    public static final String DELETED_EMAIL_VALUE = "deleted@email.com";

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"
    );

    public Email {
        validateEmail(value);
    }

    private void validateEmail(String value) {
        if (value == null || !EMAIL_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("올바른 이메일 형식이 아닙니다.");
        }
    }
}
