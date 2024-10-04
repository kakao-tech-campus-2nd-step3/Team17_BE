package homeTry.exerciseList.exception;

import homeTry.exception.InternalServerException;

public class ExerciseStartException extends InternalServerException {

    public ExerciseStartException() {
        super(ExerciseErrorType.EXERCISE_START_EXCEPTION);
    }

}
