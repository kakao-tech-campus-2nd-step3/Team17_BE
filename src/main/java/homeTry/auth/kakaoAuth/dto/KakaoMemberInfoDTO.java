package homeTry.auth.kakaoAuth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import java.util.Date;

public record KakaoMemberInfoDTO(
        @JsonProperty("id")
        Long id,

        @JsonProperty("connected_at")
        Date connectedAt,

        @JsonProperty("kakao_account")
        KakaoAccount kakaoAccount
) {
    public record KakaoAccount(
            @JsonProperty("profile_nickname_needs_agreement")
            Boolean profileNicknameNeedsAgreement,

            @JsonProperty("has_email")
            Boolean hasEmail,

            @JsonProperty("email")
            @Email(message = "올바른 이메일 형식이 아닙니다.")
            String email,

            @JsonProperty("profile")
            Profile profile
    ) {
        public record Profile(@JsonProperty("nickname") String nickname) { }
    }
}
