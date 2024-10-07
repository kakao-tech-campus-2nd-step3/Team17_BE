package homeTry.auth.exception;

import homeTry.exception.ErrorType;
import org.springframework.http.HttpStatus;

public enum AuthErrorType implements ErrorType {
    INVALID_TOKEN_EXCEPTION("Auth401_001", HttpStatus.UNAUTHORIZED, "올바르지 않은 토큰입니다."),
    INVALID_AUTH_CODE_EXCEPTION("Auth400_001", HttpStatus.BAD_REQUEST, "인가 코드가 올바르지 않습니다."),

    HOME_TRY_SERVER_EXCEPTION("Auth500_001", HttpStatus.INTERNAL_SERVER_ERROR,
        "서버 인증 기능에 예상치 못한 에러가 발생하였습니다"),
    KAKAO_AUTH_SERVER_EXCEPTION("Auth500_002", HttpStatus.INTERNAL_SERVER_ERROR,
        "카카오 서버에 의한 에러가 발생하였습니다.");

    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;

    AuthErrorType(String errorCode, HttpStatus httpStatus, String message) {
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
