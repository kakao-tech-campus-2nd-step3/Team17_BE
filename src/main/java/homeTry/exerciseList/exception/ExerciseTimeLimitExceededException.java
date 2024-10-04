package homeTry.exerciseList.exception;

import homeTry.exception.BadRequestException;

public class ExerciseTimeLimitExceededException extends BadRequestException {

    public ExerciseTimeLimitExceededException() {
        super(ExerciseErrorType.EXERCISE_TIME_LIMIT_EXCEEDED_EXCEPTION);
    }

}
