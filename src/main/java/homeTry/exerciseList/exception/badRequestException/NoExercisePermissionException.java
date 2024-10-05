package homeTry.exerciseList.exception.badRequestException;

import homeTry.exception.BadRequestException;
import homeTry.exerciseList.exception.ExerciseErrorType;

public class NoExercisePermissionException extends BadRequestException {

    public NoExercisePermissionException() {
        super(ExerciseErrorType.EXERCISE_NO_PERMISSION_EXCEPTION);
    }

}
