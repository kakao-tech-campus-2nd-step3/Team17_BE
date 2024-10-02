package homeTry.exerciseList.dto;

import homeTry.exerciseList.model.entity.Exercise;
import homeTry.exerciseList.model.entity.ExerciseHistory;
import java.time.Duration;

public record ExerciseResponse(
    Long exerciseId,
    String exerciseName,
    Duration exerciseTime,
    boolean isActive
) {

    public static ExerciseResponse fromEntity(Exercise exercise) {
        return new ExerciseResponse(
            exercise.getExerciseId(),
            exercise.getExerciseName(),
            exercise.getCurrentExerciseTime().getExerciseTime(),
            exercise.getCurrentExerciseTime().isActive()
        );
    }

    public static ExerciseResponse fromHistory(ExerciseHistory history) {
        return new ExerciseResponse(
            history.getExercise().getExerciseId(),
            history.getExercise().getExerciseName(),
            history.getExerciseHistoryTime(),
            false
        );
    }

}
