package homeTry.exerciseList.exception;

import homeTry.exception.BadRequestException;

public class ExerciseNameBlankException extends BadRequestException {

    public ExerciseNameBlankException() {
        super(ExerciseErrorType.EXERCISE_NAME_BLANK_EXCEPTION);
    }

}
