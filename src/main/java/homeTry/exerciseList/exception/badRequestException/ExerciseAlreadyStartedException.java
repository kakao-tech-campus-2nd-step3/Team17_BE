package homeTry.exerciseList.exception.badRequestException;

import homeTry.common.exception.BadRequestException;
import homeTry.exerciseList.exception.ExerciseErrorType;

public class ExerciseAlreadyStartedException extends BadRequestException {

    public ExerciseAlreadyStartedException() {
        super(ExerciseErrorType.EXERCISE_ALREADY_ACTIVE_EXCEPTION);
    }

}
