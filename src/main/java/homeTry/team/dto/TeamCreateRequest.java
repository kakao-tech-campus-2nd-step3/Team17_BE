package homeTry.team.dto;

import homeTry.tag.dto.TagDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.List;

public record TeamCreateRequest(
        @NotBlank
        @Size(min = 1, max = 15, message = "팀의 이름은 최소 1글자, 최대 15글자 입니다")
        String teamName,

        @NotBlank
        @Size(min = 1, message = "팀 설명은 필수입니다.")
        String teamDescription,

        @Min(value = 1, message = "최대 참여 인원은 1명 이상이여야 합니다")
        @Max(value = 30, message = "최대 참여 인원은 30명이 최대입니다")
        int maxParticipants,

        @Size(min = 4, max = 16, message = "패스워드는 최소 4글자, 최대 16글자 입니다.")
        String password,

        @NotEmpty
        @Valid //리스트 내부의 tagIdDTO에 대한 유효성 검사를 위한 @Valid 어노테이션 추가
        List<TagDTO> tagIdList
) {

}
