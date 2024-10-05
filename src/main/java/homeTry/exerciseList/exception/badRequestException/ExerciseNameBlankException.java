package homeTry.exerciseList.exception.badRequestException;

import homeTry.exception.BadRequestException;
import homeTry.exerciseList.exception.ExerciseErrorType;

public class ExerciseNameBlankException extends BadRequestException {

    public ExerciseNameBlankException() {
        super(ExerciseErrorType.EXERCISE_NAME_BLANK_EXCEPTION);
    }

}
