package homeTry.diary.exception;

import org.springframework.http.HttpStatus;

import homeTry.exception.ErrorType;


public enum DiaryErrorType implements ErrorType {

    MEMO_BLANK_EXCEPTION("DIARY400_001", HttpStatus.BAD_REQUEST, "메모는 공백일 수 없습니다."),
    MEMO_TOO_LONG_EXCEPTION("DIARY400_002", HttpStatus.BAD_REQUEST, "메모는 500자이내여야 합니다."),
    DIARY_NOT_FOUND_EXCEPTION("DIARY404_003", HttpStatus.NOT_FOUND, "존재하지 않는 일기입니다.");


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
