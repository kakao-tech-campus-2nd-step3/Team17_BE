package homeTry.exerciseList.dto;

import homeTry.exerciseList.model.entity.ExerciseHistory;
import homeTry.exerciseList.model.entity.ExerciseTime;
import java.time.Duration;

public record ExerciseResponse(
    Long exerciseId,
    String exerciseName,
    Duration exerciseTime,
    boolean isActive
) {

    public static ExerciseResponse fromHistory(ExerciseHistory history) {
        return new ExerciseResponse(
            history.getExercise().getExerciseId(),
            history.getExercise().getExerciseName(),
            history.getExerciseHistoryTime(),
            false
        );
    }

    public static ExerciseResponse fromTime(ExerciseTime exerciseTime) {
        return new ExerciseResponse(
            exerciseTime.getExercise().getExerciseId(),
            exerciseTime.getExercise().getExerciseName(),
            exerciseTime.getExerciseTime(),
            exerciseTime.isActive()
        );
    }

}
