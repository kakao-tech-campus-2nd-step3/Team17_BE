package homeTry.member.model.vo;

import java.util.regex.Pattern;

public record Email( String email ) {

    public Email {
        validateEmail(email);
    }

    private void validateEmail(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches())
            throw new IllegalArgumentException("올바른 이메일 형식이 아닙니다.");
    }

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@(.+)$"
    );
}
