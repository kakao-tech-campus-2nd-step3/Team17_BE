package homeTry.product.model.vo;

import jakarta.persistence.Embeddable;

@Embeddable
public record ProductPrice(Long value) {

    public ProductPrice {
        validateProductPrice(value);
    }

    private void validateProductPrice(Long price) {
        if (price == null || price <= 0) {
            throw new IllegalArgumentException("가격은 0보다 커야 합니다.");
        }
    }

    @Override
    public String toString() {
        return value.toString();
    }

}
