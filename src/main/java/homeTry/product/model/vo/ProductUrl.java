package homeTry.product.model.vo;

import jakarta.persistence.Embeddable;

@Embeddable
public record ProductUrl(String value) {

    public ProductUrl {
        validateProductUrl(value);
    }

    private void validateProductUrl(String productUrl) {
        if (productUrl == null || productUrl.isBlank()) {
            throw new IllegalArgumentException("상품 URL은 필수입니다.");
        }
    }

    @Override
    public String toString() {
        return value;
    }

}
