package homeTry.tag.productTag.dto;

import homeTry.tag.productTag.model.entity.ProductTag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductTagDto(
    
    @NotNull(message = "tagId는 필수입니다")
    @Positive(message = "tagId는 양수값이여야 합니다")
    Long tagId,
    String productTagName) {

    public static ProductTagDto from(ProductTag productTag) {
        return new ProductTagDto(productTag.getId(), productTag.getTagNameValue());
    }

}