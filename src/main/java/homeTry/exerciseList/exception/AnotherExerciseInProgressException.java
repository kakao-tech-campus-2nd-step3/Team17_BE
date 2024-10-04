package homeTry.exerciseList.exception;

import homeTry.exception.BadRequestException;

public class AnotherExerciseInProgressException extends BadRequestException {

    public AnotherExerciseInProgressException() {
        super(ExerciseErrorType.ANOTHER_EXERCISE_IN_PROGRESS_EXCEPTION);
    }

}
