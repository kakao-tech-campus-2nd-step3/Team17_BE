package homeTry.chatting.exception;

import homeTry.common.exception.ErrorType;
import org.springframework.http.HttpStatus;

public enum ChattingErrorType implements ErrorType {

    INVALID_TEAM_ID_EXCEPTION("Chatting400_001", HttpStatus.BAD_REQUEST, "올바르지 않은 팀 아이디 입니다."),

    NO_SUCH_MEMBER_IN_DB_WITH_VALID_TOKEN("Chatting400_002", HttpStatus.BAD_REQUEST,
            "인증엔 성공하였지만 회원이 아닙니다. 회원가입 해주시기 바랍니다."),

    INVALID_CHATTING_TOKEN_EXCEPTION("Chatting401_001", HttpStatus.UNAUTHORIZED, "올바르지 않은 토큰입니다."),

    INACTIVATED_MEMBER_WITH_VALID_TOKEN("Chatting403_001", HttpStatus.FORBIDDEN,
            "인증엔 성공하였지만, 비활성화 된 회원입니다."),

    UNKNOWN_CHATTING_EXCEPTION("Chatting500_001", HttpStatus.INTERNAL_SERVER_ERROR,
            "예상치 못한 서버 에러입니다.");


    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;


    ChattingErrorType(String errorCode, HttpStatus httpStatus, String message) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
