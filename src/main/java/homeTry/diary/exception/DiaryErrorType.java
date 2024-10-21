package homeTry.diary.exception;

import homeTry.common.exception.ErrorType;
import org.springframework.http.HttpStatus;


public enum DiaryErrorType implements ErrorType {

    DIARY_NOT_FOUND_EXCEPTION("DIARY404_001", HttpStatus.NOT_FOUND, "존재하지 않는 일기입니다.");

    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;

    DiaryErrorType(String errorCode, HttpStatus httpStatus, String message) {
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
