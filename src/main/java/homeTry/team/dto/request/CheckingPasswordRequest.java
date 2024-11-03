package homeTry.team.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CheckingPasswordRequest(
        @NotBlank(message = "비밀번호가 비어있을 수는 없습니다")
        @Size(max = 10, message = "비밀번호의 최대 길이는 10글자 입니다")
        String password
) {
}
