package homeTry.product.dto.response;

import homeTry.product.model.entity.Product;
import homeTry.tag.productTag.dto.ProductTagDto;

public record ProductResponse(
    Long productId,
    String imageUrl,
    String productUrl,
    String name,
    Long price,
    String storeName,
    ProductTagDto tag
) {

    public static ProductResponse from(Product product, ProductTagDto productTag) {
        return new ProductResponse(
            product.getId(),
            product.getImageUrl(),
            product.getProductUrl(),
            product.getName(),
            product.getPrice(),
            product.getStoreName(),
            productTag
        );
    }

}
