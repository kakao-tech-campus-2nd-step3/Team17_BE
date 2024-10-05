package homeTry.exerciseList.exception;

import homeTry.exception.BadRequestException;

public class ExerciseNotStartedException extends BadRequestException {

    public ExerciseNotStartedException() {
        super(ExerciseErrorType.EXERCISE_NOT_ACTIVE_EXCEPTION);
    }

}
