package homeTry.admin.exception;

import homeTry.common.exception.ErrorType;
import org.springframework.http.HttpStatus;

public enum AdminErrorType implements ErrorType {

    INVALID_ADMIN_CODE_EXCEPTION("Admin403_001", HttpStatus.FORBIDDEN, "관리자 코드가 올바르지 않습니다.");

    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;

    AdminErrorType(String errorCode, HttpStatus httpStatus, String message) {
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
