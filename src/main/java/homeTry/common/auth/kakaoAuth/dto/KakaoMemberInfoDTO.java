package homeTry.common.auth.kakaoAuth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.Email;
import java.util.Date;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record KakaoMemberInfoDTO(
    Long id,

    Date connectedAt,

    KakaoAccount kakaoAccount
) {

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record KakaoAccount(
        Boolean profileNicknameNeedsAgreement,

        Boolean hasEmail,

        @Email(message = "올바른 이메일 형식이 아닙니다.")
        String email,

        Profile profile
    ) {

        public record Profile(String nickname) {

        }
    }
}
