package homeTry.chatting.controller;

import homeTry.chatting.dto.request.ChattingMessageRequest;
import homeTry.chatting.dto.response.ChattingMessageResponse;
import homeTry.chatting.service.ChattingService;
import homeTry.common.annotation.LoginMember;
import homeTry.member.dto.MemberDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Controller
public class ChattingController {

    private final ChattingService chattingService;

    private static final Logger logger = LoggerFactory.getLogger(ChattingController.class);


    public ChattingController(ChattingService chattingService) {
        this.chattingService = chattingService;
    }

    @GetMapping("/api/chatting/{teamId}")
    public ResponseEntity<Slice<ChattingMessageResponse>> getChatMessages(
            @PathVariable("teamId") Long teamId,
            @LoginMember MemberDTO memberDTO, Pageable pageable) {

        return new ResponseEntity<>(
                chattingService.getChattingMessageSlice(teamId, memberDTO, pageable),
                HttpStatus.OK);
    }

    //메시지 송신 및 수신, /pub 을 생략할 수 있음.
    // 클라이언트 단에선 /api/chatting/websocket/pub/{teamId} 로 요청
    @MessageMapping("/{teamId}")
    @SendTo("/sub/{teamId}")
    public ChattingMessageResponse receiveMessage(@DestinationVariable("teamId") Long teamId,
            ChattingMessageRequest chattingMessageRequest, SimpMessageHeaderAccessor accessor) {

        logger.debug("Message received for team {}: {}", teamId, chattingMessageRequest);

        //세션에 member 꺼내오기 (Http 컨트롤러의 ArgumentResolver랑 비슷한 역할)
        MemberDTO memberDTO = (MemberDTO) accessor.getSessionAttributes().get("member");

        //db에 저장 & 저장한 채팅 메세지 DTO 클라이언트에게 보내기
        return chattingService.saveChattingMessage(teamId, chattingMessageRequest, memberDTO);
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        logger.info("새로운 웹소켓 연결이 생성되었습니다!");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        logger.info("웹소켓 연결이 종료되었습니다!");
    }


}
