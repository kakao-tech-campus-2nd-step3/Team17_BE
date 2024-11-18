package homeTry.product.dto.response;

import homeTry.product.model.entity.Product;
import homeTry.tag.productTag.dto.ProductTagDto;

public record ProductAdminResponse(
    Long productId,
    Long viewCount,
    String imageUrl,
    String productUrl,
    String name,
    Long price,
    String storeName,
    ProductTagDto tag
) {

    public static ProductAdminResponse from(Product product, ProductTagDto productTag) {
        return new ProductAdminResponse(
            product.getId(),
            product.getViewCount(),
            product.getImageUrl(),
            product.getProductUrl(),
            product.getName(),
            product.getPrice(),
            product.getStoreName(),
            productTag
        );
    }

}
