package homeTry.exerciseList.exception.badRequestException;

import homeTry.exception.BadRequestException;
import homeTry.exerciseList.exception.ExerciseErrorType;

public class AnotherExerciseInProgressException extends BadRequestException {

    public AnotherExerciseInProgressException() {
        super(ExerciseErrorType.ANOTHER_EXERCISE_IN_PROGRESS_EXCEPTION);
    }

}
