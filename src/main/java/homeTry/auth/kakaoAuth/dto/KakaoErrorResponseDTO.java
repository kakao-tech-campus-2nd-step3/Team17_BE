package homeTry.auth.kakaoAuth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KakaoErrorResponseDTO(
        @JsonProperty("error")
        String error,

        @JsonProperty("error_description")
        String errorDescription,

        @JsonProperty("error_code")
        String KakaoErrorCode
) { }
