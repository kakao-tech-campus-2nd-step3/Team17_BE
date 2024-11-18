package homeTry.tag.exception;

import homeTry.common.exception.ErrorType;
import org.springframework.http.HttpStatus;

public enum TagErrorType implements ErrorType {

    FORBIDDEN_TAG_ACCESS_EXCEPTION("TAG403_001", HttpStatus.FORBIDDEN, "관리자 권한이 필요합니다.");

    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;

    TagErrorType(String errorCode, HttpStatus httpStatus, String message) {
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
