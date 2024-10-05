package homeTry.exerciseList.exception;

import homeTry.exception.BadRequestException;

public class ExerciseNameTooLongException extends BadRequestException {

    public ExerciseNameTooLongException() {
        super(ExerciseErrorType.EXERCISE_NAME_TOO_LONG_EXCEPTION);
    }

}
