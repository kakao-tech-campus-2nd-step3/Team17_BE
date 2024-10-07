package homeTry.auth.kakaoAuth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoErrorResponseDTO(
    String error,

    String errorDescription,

    @JsonProperty("error_code")
    String KakaoErrorCode
) {

}
