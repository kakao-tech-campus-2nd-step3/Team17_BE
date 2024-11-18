package homeTry.team.exception;

import homeTry.common.exception.ErrorType;
import org.springframework.http.HttpStatus;

public enum TeamErrorType implements ErrorType {
    //400 에러
    NOT_TEAM_LEADER_EXCEPTION("Team400_001", HttpStatus.BAD_REQUEST, "리더만이 삭제할 수 있습니다."),
    TEAM_HAS_NOT_PASSWORD_EXCEPTION("Team400_002", HttpStatus.BAD_REQUEST, "해당 팀에는 비밀번호가 없습니다"),
    INVALID_PASSWORD_EXCEPTION("Team400_003", HttpStatus.BAD_REQUEST, "비밀번호가 틀렸습니다"),
    TEAM_NAME_ALREADY_EXISTS_EXCEPTION("Team400_004", HttpStatus.BAD_REQUEST, "이미 동일한 이름의 팀이 존재합니다"),
    ALREADY_JOINED_EXCEPTION("Team400_005", HttpStatus.BAD_REQUEST, "이미 해당 멤버는 팀에 가입되어 있습니다"),
    TEAM_LEADER_CANNOT_WITHDRAW_EXCEPTION("Team400_006", HttpStatus.BAD_REQUEST, "팀 리더는 팀에서 탈퇴할 수 없습니다"),
    TEAM_PARTICIPANTS_FULLED_EXCEPTION("Team400_007", HttpStatus.BAD_REQUEST, "팀 인원이 가득차서 가입이 불가합니다"),

    //404 에러
    TEAM_NOT_FOUND_EXCEPTION("Team404_001", HttpStatus.NOT_FOUND, "해당 팀을 찾을 수 없습니다"),
    MY_RANKING_NOT_FOUND_EXCEPTION("Team404_002", HttpStatus.NOT_FOUND, "요청 멤버의 랭킹을 찾을 수 없습니다"),
    TEAMMEMBER_NOT_FOUND_EXCEPTION("Team404_003", HttpStatus.NOT_FOUND, "해당 팀멤버를 찾을 수 없습니다");

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
