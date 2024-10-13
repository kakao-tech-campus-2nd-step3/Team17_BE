package homeTry.exerciseList.exception.badRequestException;

import homeTry.common.exception.BadRequestException;
import homeTry.exerciseList.exception.ExerciseErrorType;

public class ExerciseInProgressException extends BadRequestException {

    public ExerciseInProgressException() {
        super(ExerciseErrorType.EXERCISE_IN_PROGRESS_EXCEPTION);
    }

}
