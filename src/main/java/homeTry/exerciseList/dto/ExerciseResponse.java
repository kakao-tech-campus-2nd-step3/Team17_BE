package homeTry.exerciseList.dto;

import homeTry.exerciseList.model.entity.Exercise;
import homeTry.exerciseList.model.entity.ExerciseHistory;
import homeTry.exerciseList.model.entity.ExerciseTime;
import java.time.Duration;

public record ExerciseResponse(
    Long exerciseId,
    String exerciseName,
    Duration exerciseTime,
    boolean isActive
) {

    public static ExerciseResponse from(Exercise exercise, Duration exerciseTime,
        boolean isActive) {
        return new ExerciseResponse(
            exercise.getExerciseId(),
            exercise.getExerciseName(),
            exerciseTime,
            isActive
        );
    }

    public static ExerciseResponse from(ExerciseHistory history) {
        return from(history.getExercise(), history.getExerciseHistoryTime(), false);
    }

    public static ExerciseResponse from(ExerciseTime exerciseTime) {
        return from(exerciseTime.getExercise(), exerciseTime.getExerciseTime(),
            exerciseTime.isActive());
    }

}
