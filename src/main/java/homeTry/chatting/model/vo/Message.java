package homeTry.chatting.model.vo;

public record Message(String value) {

    public Message {
        validateMessage(value);
    }

    private void validateMessage(String value) {
        if (value == null || value.isBlank()){
            throw new IllegalArgumentException("메세지를 입력해주세요.");
        }

        if (value.length() > 500) {
            throw new IllegalArgumentException("메세지의 길이는 최대 500자 입니다.");
        }

    }

}
