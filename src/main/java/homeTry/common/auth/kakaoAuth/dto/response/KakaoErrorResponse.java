package homeTry.common.auth.kakaoAuth.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoErrorResponse(
    String error,

    String errorDescription,

    @JsonProperty("error_code")
    String KakaoErrorCode
) {

}
