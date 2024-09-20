package homeTry.member.model.vo;

public record Password(String password) {

    public Password {
        validatePassword(password);
    }

    private void validatePassword(String password) {
        if (password == null || password.isBlank())
            throw new IllegalArgumentException("비밀번호를 입력해주세요.");

        if (password.length() > 255)
            throw new IllegalArgumentException("비밀번호가 너무 깁니다.");
    }
}
