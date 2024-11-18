package homeTry.product.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ProductRequest(
    @NotBlank
    String imageUrl,
    @NotBlank
    String productUrl,
    @NotBlank
    @Size(min = 1, max = 20, message = "상품의 이름은 최소 1글자, 최대 20글자 입니다")
    String name,
    @NotNull
    Long price,
    @NotBlank
    @Size(min = 1, max = 15, message = "상점의 이름은 최소 1글자, 최대 15글자 입니다")
    String storeName,
    @NotNull
    Long tagId
) {

}
