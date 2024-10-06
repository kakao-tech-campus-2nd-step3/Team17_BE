package homeTry.exerciseList.exception.badRequestException;

import homeTry.exception.BadRequestException;
import homeTry.exerciseList.exception.ExerciseErrorType;

public class ExerciseNotFoundException extends BadRequestException {

    public ExerciseNotFoundException() {
        super(ExerciseErrorType.EXERCISE_NOT_FOUND_EXCEPTION);
    }

}
