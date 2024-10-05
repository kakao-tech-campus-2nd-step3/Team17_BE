package homeTry.exerciseList.exception;

import homeTry.exception.BadRequestException;

public class NoExercisePermissionException extends BadRequestException {

    public NoExercisePermissionException() {
        super(ExerciseErrorType.EXERCISE_NO_PERMISSION_EXCEPTION);
    }

}
