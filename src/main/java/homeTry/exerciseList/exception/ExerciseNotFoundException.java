package homeTry.exerciseList.exception;

import homeTry.exception.BadRequestException;

public class ExerciseNotFoundException extends BadRequestException {

    public ExerciseNotFoundException() {
        super(ExerciseErrorType.EXERCISE_NOT_FOUND_EXCEPTION);
    }

}
