package homeTry.product.dto.response;

import homeTry.product.model.entity.Product;

public record ProductResponse(
    Long productId,
    String imageUrl,
    String productUrl,
    String name,
    Long price,
    String storeName
) {

    public static ProductResponse from(Product product) {
        return new ProductResponse(
            product.getId(),
            product.getImageUrl(),
            product.getProductUrl(),
            product.getName(),
            product.getPrice(),
            product.getStoreName()
        );
    }

}
