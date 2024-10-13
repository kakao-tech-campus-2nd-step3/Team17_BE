package homeTry.exerciseList.exception;

import homeTry.common.exception.ErrorType;
import org.springframework.http.HttpStatus;

public enum ExerciseErrorType implements ErrorType {

    // 운동 리스트를 찾을 수 없을 때 - 404
    EXERCISE_NOT_FOUND_EXCEPTION("Exercise404_001", HttpStatus.NOT_FOUND, "해당 운동을 찾을 수 없습니다."),
    // 운동이 이미 진행 중일 때 - 400
    EXERCISE_ALREADY_ACTIVE_EXCEPTION("Exercise400_001", HttpStatus.BAD_REQUEST, "이미 운동을 진행 중입니다."),
    // 운동이 시작되지 않았는데 종료를 시도할 때 - 400
    EXERCISE_NOT_ACTIVE_EXCEPTION("Exercise400_002", HttpStatus.BAD_REQUEST, "운동이 시작되지 않았습니다."),
    // 한 번에 운동 시간 제한 초과 - 400
    EXERCISE_TIME_LIMIT_EXCEEDED_EXCEPTION("Exercise400_003", HttpStatus.BAD_REQUEST,
            "한 번에 저장되는 운동 시간은 8시간을 초과할 수 없습니다."),
    // 하루 운동 시간 제한 초과 - 400
    DAILY_EXERCISE_TIME_LIMIT_EXCEEDED_EXCEPTION("Exercise400_004", HttpStatus.BAD_REQUEST,
            "하루 전체 운동 시간은 12시간을 초과할 수 없습니다."),
    // 삭제한 운동을 시작하려고 할 때 - 400
    EXERCISE_DEPRECATED_EXCEPTION("Exercise400_005", HttpStatus.BAD_REQUEST,
            "이 운동은 삭제된 운동으로, 운동을 시작할 수 없습니다."),
    // 운동이 실행 중일 때, 운동을 삭제하려는 경우 - 400
    EXERCISE_IN_PROGRESS_EXCEPTION("Exercise400_006", HttpStatus.BAD_REQUEST,
            "운동이 실행 중이므로 삭제할 수 없습니다."),
    // 운동에 대한 권한이 없을 때 - 403
    EXERCISE_NO_PERMISSION_EXCEPTION("Exercise403_001", HttpStatus.FORBIDDEN,
            "이 운동에 대한 실행 권한이 없습니다.");

    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;

    ExerciseErrorType(String errorCode, HttpStatus httpStatus, String message) {
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
