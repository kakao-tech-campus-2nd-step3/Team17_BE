package homeTry.tag.model.vo;

public record TagName(String value) {

    public TagName {
        validateTagName(value);
    }

    private void validateTagName(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("태그 이름값은 필수입니다");
        }
        if (value.length() > 15) {
            throw new IllegalArgumentException("태그의 길이는 최대 15자 입니다");
        }
    }
}
