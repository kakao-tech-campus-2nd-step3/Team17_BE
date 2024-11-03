package homeTry.product.model.vo;

import jakarta.persistence.Embeddable;

@Embeddable
public record ProductImageUrl(String value) {

    public ProductImageUrl {
        validateProductImageUrl(value);
    }

    private void validateProductImageUrl(String imageUrl) {
        if (imageUrl == null || imageUrl.isBlank()) {
            throw new IllegalArgumentException("이미지 URL은 필수입니다.");
        }
    }

    @Override
    public String toString() {
        return value;
    }

}
