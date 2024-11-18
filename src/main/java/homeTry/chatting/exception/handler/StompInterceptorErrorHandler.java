package homeTry.chatting.exception.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import homeTry.chatting.exception.ChattingErrorType;
import homeTry.chatting.exception.badRequestException.InvalidChattingTokenException;
import homeTry.common.exception.BadRequestException;
import homeTry.common.exception.ErrorType;
import homeTry.common.exception.dto.response.ErrorResponse;
import java.nio.charset.StandardCharsets;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler;

@Component
public class StompInterceptorErrorHandler extends StompSubProtocolErrorHandler {

    /*
     * StompInterCeptor에서 일어난 예외를 핸들링 하는 클래스
     *
     * 1. StompInterCeptor에서 일어난 예외는 MessageDeliveryException 로 감싸져서 전달된다.
     * 2. 그래서 MessageDeliveryException의 Root Cause를 찾는다. (By import org.apache.commons.lang3.exception.ExceptionUtils)
     * 3. 이후 Root Cause 예외 핸들링을 한다.
     *
     */

    private static final Logger logger = LoggerFactory.getLogger(
            StompInterceptorErrorHandler.class);
    private final ObjectMapper objectMapper;

    @Autowired
    public StompInterceptorErrorHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Message<byte[]> handleClientMessageProcessingError(Message<byte[]> clientMessage,
            Throwable ex) {
        Throwable cause = ExceptionUtils.getRootCause(ex);

        /*
        * Java 13 : yield 키워드 == return + break
        * Java 17 : Switch 패턴 매칭
        */
        return switch (cause) {
            case InvalidChattingTokenException e ->
                    createErrorMessage(handleError(e.getErrorType()));

            case BadRequestException e -> {
                logger.error("인터셉터 에러 핸들러에서 에러 발생 | {} - {}", e.getErrorType().getErrorCode(),
                        e.getErrorType().getMessage());
                yield createErrorMessage(handleError(e.getErrorType()));
            }

            case null -> {
                logger.error("인터셉터 에러 핸들러에서 예상치 못한 인터셉터 에러 발생했지만 Root Cause가 null임");
                yield createErrorMessage(handleError(ChattingErrorType.UNKNOWN_CHATTING_EXCEPTION));
            }

            default -> {
                logger.error("인터셉터 에러 핸들러에서 예상치 못한 인터셉터 에러 발생 {} - {}",
                        cause.getMessage(),
                        cause.getClass().getName());
                yield createErrorMessage(handleError(ChattingErrorType.UNKNOWN_CHATTING_EXCEPTION));
            }
        };
    }

    private Message<byte[]> createErrorMessage(String errorMessage) {
        StompHeaderAccessor accessor = StompHeaderAccessor.create(StompCommand.ERROR);
        accessor.setLeaveMutable(true);
        return MessageBuilder.createMessage(
                errorMessage.getBytes(StandardCharsets.UTF_8),
                accessor.getMessageHeaders()
        );
    }

    private String handleError(ErrorType errorType) {
        ErrorResponse errorResponse = new ErrorResponse(
                errorType.getErrorCode(),
                errorType.getMessage()
        );
        return convertErrorResponseToJson(errorResponse);
    }

    private String convertErrorResponseToJson(ErrorResponse errorResponse) {
        try {
            return objectMapper.writeValueAsString(errorResponse);
        } catch (JsonProcessingException e) {
            logger.error("StompInterceptor Json 매핑 에러 발생 : {}", e.getMessage());
            return ChattingErrorType.UNKNOWN_CHATTING_EXCEPTION.getMessage();
        }
    }
}