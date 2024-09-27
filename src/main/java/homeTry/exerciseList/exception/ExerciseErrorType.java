package homeTry.exerciseList.exception;

import homeTry.exception.ErrorType;
import org.springframework.http.HttpStatus;

public enum ExerciseErrorType implements ErrorType {

    // 1. 운동 리스트를 찾을 수 없을 때 - 404
    EXERCISE_NOT_FOUND_EXCEPTION("ExerciseList404_001", HttpStatus.NOT_FOUND, "해당 운동을 찾을 수 없습니다."),
    // 2. 운동이 이미 진행 중일 때 - 400
    EXERCISE_ALREADY_ACTIVE_EXCEPTION("ExerciseList400_001", HttpStatus.BAD_REQUEST, "이미 운동을 진행 중입니다."),
    // 3. 운동이 시작되지 않았는데 종료를 시도할 때 - 400
    EXERCISE_NOT_ACTIVE_EXCEPTION("ExerciseList400_002", HttpStatus.BAD_REQUEST, "운동이 시작되지 않았습니다."),
    // 4. 운동에 대한 권한이 없을 때 - 403
    EXERCISE_NO_PERMISSION_EXCEPTION("ExerciseList403_001", HttpStatus.FORBIDDEN, "이 운동에 대한 실행 권한이 없습니다.");

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