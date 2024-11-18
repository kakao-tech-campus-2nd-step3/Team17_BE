package homeTry.team.dto.request;

import homeTry.common.annotation.PasswordValid;
import homeTry.tag.teamTag.dto.TeamTagDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public record TeamCreateRequest(
        @NotBlank
        @Size(min = 1, max = 15, message = "팀의 이름은 최소 1글자, 최대 15글자 입니다")
        String teamName,

        @NotBlank
        @Size(min = 1, max = 255, message = "팀 설명은 최소 1글자 최대 255글자 입니다")
        String teamDescription,

        @Min(value = 1, message = "최대 참여 인원은 1명 이상이여야 합니다")
        @Max(value = 30, message = "최대 참여 인원은 30명이 최대입니다")
        int maxParticipants,

        @PasswordValid
        @Size(min = 4, max = 16, message = "패스워드는 최소 4글자, 최대 16글자 입니다.")
        String password,

        List<Long> tagIdList
) {

}
