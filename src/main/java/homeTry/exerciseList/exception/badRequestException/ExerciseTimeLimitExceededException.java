package homeTry.exerciseList.exception.badRequestException;

import homeTry.exception.BadRequestException;
import homeTry.exerciseList.exception.ExerciseErrorType;

public class ExerciseTimeLimitExceededException extends BadRequestException {

    public ExerciseTimeLimitExceededException() {
        super(ExerciseErrorType.EXERCISE_TIME_LIMIT_EXCEEDED_EXCEPTION);
    }

}
