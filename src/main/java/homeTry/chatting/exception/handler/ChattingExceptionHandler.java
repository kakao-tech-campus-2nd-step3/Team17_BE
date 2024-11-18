package homeTry.chatting.exception.handler;

import homeTry.chatting.exception.ChattingErrorType;
import homeTry.common.exception.BadRequestException;
import homeTry.common.exception.CommonErrorType;
import homeTry.common.exception.dto.response.ErrorResponse;
import java.util.Timer;
import java.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@ControllerAdvice
public class ChattingExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ChattingExceptionHandler.class);

    private final ApplicationEventPublisher eventPublisher;

    private static final String ERROR_DESTINATION = "/queue/errors";
    private static final Integer SLEEP_TIMER = 1000;


    @Autowired
    public ChattingExceptionHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }


    /**
     * 채팅 @MessageMapping인 메서드에서 생기는 예외를 처리.
     * 1. 에러가 발생하면
     * 2. ErrorResponse 를 보내고
     * 3. 세션을 강제 종료시킴
     */

    private void sleepAndDisconnectClient(String sessionId) {
        // 에러 메시지 전송 후 지연 후 연결 끊기
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                disconnectClient(sessionId);
            }
        }, SLEEP_TIMER); // 1초 후에 연결 끊기
    }

    private void disconnectClient(String sessionId) {
        // SessionDisconnectEvent를 발생시켜 클라이언트 연결 종료
        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.DISCONNECT);
        accessor.setSessionId(sessionId);

        // MessageBuilder를 사용하여 메시지 생성
        var message = MessageBuilder.createMessage(new byte[0], accessor.getMessageHeaders());

        // SessionDisconnectEvent 발행
        eventPublisher.publishEvent(new SessionDisconnectEvent(this, message, sessionId, null));
    }

    @MessageExceptionHandler(BadRequestException.class)
    @SendToUser(ERROR_DESTINATION)
    public ErrorResponse handleException(BadRequestException e,
            SimpMessageHeaderAccessor accessor) {

        sleepAndDisconnectClient(accessor.getSessionId());

        return new ErrorResponse(e.getErrorType().getErrorCode(), e.getErrorType().getMessage());
    }

    @MessageExceptionHandler(IllegalArgumentException.class)
    @SendToUser(ERROR_DESTINATION)
    public ErrorResponse handleException(IllegalArgumentException e,
            SimpMessageHeaderAccessor accessor) {

        sleepAndDisconnectClient(accessor.getSessionId());

        return new ErrorResponse(CommonErrorType.ILLEGAL_ARGUMENT_EXCEPTION.getErrorCode(), e.getMessage());
    }

    @MessageExceptionHandler(Exception.class)
    @SendToUser(ERROR_DESTINATION)
    public ErrorResponse handleException(Exception e, SimpMessageHeaderAccessor accessor) {
        logger.error("채팅 예외 핸들러에서 예상치 못한 에러 발생 : {}", e.getMessage());

        ChattingErrorType chattingErrorType = ChattingErrorType.UNKNOWN_CHATTING_EXCEPTION;

        sleepAndDisconnectClient(accessor.getSessionId());

        return new ErrorResponse(chattingErrorType.getErrorCode(),
                chattingErrorType.getMessage());
    }
}
