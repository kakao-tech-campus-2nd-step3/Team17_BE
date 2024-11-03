package homeTry.product.model.vo;

import jakarta.persistence.Embeddable;

@Embeddable
public record StoreName(String value) {

    public StoreName {
        validateStoreName(value);
    }

    private void validateStoreName(String storeName) {
        if (storeName == null || storeName.isBlank()) {
            throw new IllegalArgumentException("상점 이름은 필수입니다.");
        }

        if (storeName.length() > 15) {
            throw new IllegalArgumentException("상점 이름의 길이는 최대 15자 입니다");
        }
    }

    @Override
    public String toString() {
        return value;
    }

}
