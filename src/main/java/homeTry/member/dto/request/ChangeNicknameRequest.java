package homeTry.member.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChangeNicknameRequest(
        @NotBlank(message = "닉네임을 입력해주세요.")
        @Size(max = 32, message = "닉네임의 길이는 32자를 넘을 수 없습니다.")
        String name
) {

}