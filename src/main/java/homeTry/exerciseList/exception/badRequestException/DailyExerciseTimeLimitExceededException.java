package homeTry.exerciseList.exception.badRequestException;

import homeTry.common.exception.BadRequestException;
import homeTry.exerciseList.exception.ExerciseErrorType;

public class DailyExerciseTimeLimitExceededException extends BadRequestException {

    public DailyExerciseTimeLimitExceededException() {
        super(ExerciseErrorType.DAILY_EXERCISE_TIME_LIMIT_EXCEEDED_EXCEPTION);
    }

}
