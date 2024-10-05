package homeTry.exerciseList.exception;

import homeTry.exception.BadRequestException;

public class ExerciseAlreadyStartedException extends BadRequestException {

    public ExerciseAlreadyStartedException() {
        super(ExerciseErrorType.EXERCISE_ALREADY_ACTIVE_EXCEPTION);
    }

}
