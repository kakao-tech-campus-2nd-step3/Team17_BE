package homeTry.exerciseList.exception.badRequestException;

import homeTry.common.exception.BadRequestException;
import homeTry.exerciseList.exception.ExerciseErrorType;

public class ExerciseDeprecatedException extends BadRequestException {

    public ExerciseDeprecatedException() {
        super(ExerciseErrorType.EXERCISE_DEPRECATED_EXCEPTION);
    }

}
