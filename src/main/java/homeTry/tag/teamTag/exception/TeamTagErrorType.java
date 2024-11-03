package homeTry.tag.teamTag.exception;

import org.springframework.http.HttpStatus;

import homeTry.common.exception.ErrorType;

public enum TeamTagErrorType implements ErrorType {

    TEAM_TAG_NOT_FOUND_EXCEPTION("TEAMTAG404_001", HttpStatus.NOT_FOUND, "존재하지 않는 팀태그입니다.");
    
    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;

    TeamTagErrorType(String errorCode, HttpStatus httpStatus, String message) {
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
