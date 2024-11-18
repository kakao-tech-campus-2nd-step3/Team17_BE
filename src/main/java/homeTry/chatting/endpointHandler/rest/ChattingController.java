package homeTry.chatting.endpointHandler.rest;

import homeTry.chatting.dto.response.ChattingMessageResponse;
import homeTry.chatting.service.ChattingService;
import homeTry.common.annotation.LoginMember;
import homeTry.member.dto.MemberDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Chatting Rest", description = "채팅에 대한 Http 요청을 핸들링 하는 API")
@RestController
@RequestMapping("/api/team/chatting")
public class ChattingController {

    private final ChattingService chattingService;

    public ChattingController(ChattingService chattingService) {
        this.chattingService = chattingService;
    }

    @GetMapping("/{teamId}")
    @Operation(summary = "채팅 불러오기", description = "클라이언트가 teamId인 팀에 있을 때 해당 teamId의 채팅들을 응답한다")
    @ApiResponse(responseCode = "200", description = "채팅을 성공적으로 불러옴")
    public ResponseEntity<Slice<ChattingMessageResponse>> getChatMessages(
            @PathVariable("teamId") Long teamId,
            @LoginMember MemberDTO memberDTO,
            @SortDefault(sort="createdAt", direction = Direction.DESC) Pageable pageable) {

        return new ResponseEntity<>(
                chattingService.getChattingMessageSlice(teamId, memberDTO, pageable),
                HttpStatus.OK);
    }

}
