package homeTry.member.model.vo;

public record Nickname(String nickname) {
    private void validateNickname(String nickname) {
        if (nickname == null || nickname.isBlank())
            throw new IllegalArgumentException("닉네임을 입력해주세요.");

        if(nickname.length() > 255)
            throw new IllegalArgumentException("닉네임이 너무 깁니다.");
    }
}
