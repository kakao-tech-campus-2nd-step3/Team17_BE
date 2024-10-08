package homeTry.team.exception;

import homeTry.common.exception.ErrorType;
import org.springframework.http.HttpStatus;

public enum TeamErrorType implements ErrorType {
    TEAM_NOT_FOUND_EXCEPTION("Team404_001", HttpStatus.NOT_FOUND, "해당 팀을 찾을 수 없습니다"),
    TEAM_NAME_ALREADY_EXISTS_EXCEPTION("Team409_001", HttpStatus.CONFLICT, "이미 동일한 이름의 팀이 존재합니다"),
    NOT_TEAM_LEADER_EXCEPTION("Team401_001", HttpStatus.UNAUTHORIZED, "리더만이 삭제할 수 있습니다."),
    TEAMMEMBER_NOT_FOUND_EXCEPTION("Team500_001", HttpStatus.NOT_FOUND, "해당 팀멤버를 찾을 수 없습니다"),
    MY_RANKING_NOT_FOUND_EXCEPTION("Team404_002", HttpStatus.NOT_FOUND, "요청 멤버의 랭킹을 찾을 수 없습니다");

    private String errorCode;
    private HttpStatus httpStatus;
    private String message;

    TeamErrorType(String errorCode, HttpStatus httpStatus, String message) {
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
