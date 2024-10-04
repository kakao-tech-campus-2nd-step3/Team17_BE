package homeTry.member.exception;

import homeTry.exception.ErrorType;
import org.springframework.http.HttpStatus;

public enum MemberErrorType implements ErrorType {

    MEMBER_NOT_FOUND_EXCEPTION("Member400_001", HttpStatus.BAD_REQUEST, "유저를 찾을 수 없습니다."),
    BAD_ARGUMENT_EXCEPTION("Member400_002", HttpStatus.BAD_REQUEST, "올바르지 않은 회원 정보 입니다"), //동적 에러 메시지
    LOGIN_FAILED_EXCEPTION("Member401_001", HttpStatus.UNAUTHORIZED, "로그인에 실패하였습니다."),
    REGISTER_EMAIL_CONFLICT_EXCEPTION("Member409_001", HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),

    DB_UNIQUE_KEY_VIOLATON_EXCEPTION("Member500_001", HttpStatus.INTERNAL_SERVER_ERROR, "서버 Member DB에 에러가 발생하였습니다"),
    UNKNOWN_FATAL_EXCEPTION("Member500_002", HttpStatus.INTERNAL_SERVER_ERROR, "서버 Member 기능에 예상치 못한 에러가 발생하였습니다");

    private final String errorCode;
    private final HttpStatus httpStatus;
    private String message;

    MemberErrorType(String errorCode, HttpStatus httpStatus, String message) {
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

    public void setMessage(String message){
        this.message = message;
    }
}
