package homeTry.tag.productTag.dto.response;

import java.util.List;

import homeTry.tag.productTag.dto.ProductTagDto;

public record ProductTagResponse(
    List<ProductTagDto> productTags
) {
}
