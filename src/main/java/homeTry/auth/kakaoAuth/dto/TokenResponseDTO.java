package homeTry.auth.kakaoAuth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record TokenResponseDTO(
        @NotNull
        @JsonProperty("access_token")
        String accessToken,

        @NotNull
        @JsonProperty("token_type")
        String tokenType,

        @NotNull
        @JsonProperty("refresh_token")
        String refreshToken,

        @NotNull
        @JsonProperty("id_token")
        String idToken,

        @NotNull
        @JsonProperty("expires_in")
        Integer expiresIn,

        @NotNull
        @JsonProperty("refresh_token_expires_in")
        Integer refreshTokenExpiresIn,

        @NotNull
        String scope
) { }
