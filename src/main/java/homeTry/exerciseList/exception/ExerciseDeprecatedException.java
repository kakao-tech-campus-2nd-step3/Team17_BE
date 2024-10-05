package homeTry.exerciseList.exception;

import homeTry.exception.BadRequestException;

public class ExerciseDeprecatedException extends BadRequestException {

    public ExerciseDeprecatedException() {
        super(ExerciseErrorType.EXERCISE_DEPRECATED_EXCEPTION);
    }

}
