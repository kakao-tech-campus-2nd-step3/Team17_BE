package homeTry.exerciseList.exception.badRequestException;

import homeTry.common.exception.BadRequestException;
import homeTry.exerciseList.exception.ExerciseErrorType;

public class ExerciseNotStartedException extends BadRequestException {

    public ExerciseNotStartedException() {
        super(ExerciseErrorType.EXERCISE_NOT_ACTIVE_EXCEPTION);
    }

}
