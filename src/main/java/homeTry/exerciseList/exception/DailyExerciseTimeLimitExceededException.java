package homeTry.exerciseList.exception;

import homeTry.exception.BadRequestException;

public class DailyExerciseTimeLimitExceededException extends BadRequestException {

    public DailyExerciseTimeLimitExceededException() {
        super(ExerciseErrorType.DAILY_EXERCISE_TIME_LIMIT_EXCEEDED_EXCEPTION);
    }

}
