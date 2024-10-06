package homeTry.exerciseList.exception.badRequestException;

import homeTry.exception.BadRequestException;
import homeTry.exerciseList.exception.ExerciseErrorType;

public class ExerciseNameTooLongException extends BadRequestException {

    public ExerciseNameTooLongException() {
        super(ExerciseErrorType.EXERCISE_NAME_TOO_LONG_EXCEPTION);
    }

}
