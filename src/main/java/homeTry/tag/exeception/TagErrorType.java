package homeTry.tag.exeception;

import homeTry.exception.ErrorType;
import org.springframework.http.HttpStatus;

public enum TagErrorType implements ErrorType {
    TAG_NOT_FOUND_EXCEPTION("Tag404_001", HttpStatus.NOT_FOUND, "해당 태그를 찾을 수 없습니다");

    private String errorCode;
    private HttpStatus httpStatus;
    private String message;

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
