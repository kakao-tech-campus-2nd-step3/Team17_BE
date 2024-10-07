package homeTry.mainPage.exception;

import org.springframework.http.HttpStatus;

import homeTry.exception.ErrorType;

public enum MainPageErrorType implements ErrorType {

    INVALID_DATE_EXCEPTION("MAINPAGE400_001", HttpStatus.BAD_REQUEST, "유효하지 않은 날짜입니다.");

    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;

    MainPageErrorType(String errorCode, HttpStatus httpStatus, String message) {
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
