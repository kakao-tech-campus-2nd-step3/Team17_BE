package homeTry.tag.productTag.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProductTagRequest(

    @NotBlank(message = "팀태그 이름값은 필수입니다.")
    @Size(max = 15, message = "팀태그 이름의 길이는 최대 15자 입니다.")
    String productTagName) {
    
}
