package homeTry.exerciseList.dto;

import homeTry.exerciseList.model.entity.Exercise;
import homeTry.exerciseList.model.entity.ExerciseHistory;
import homeTry.exerciseList.model.entity.ExerciseTime;
import java.time.Duration;

public record ExerciseDto(
    Long exerciseId,
    String exerciseName,
    Duration exerciseTime,
    boolean isActive
) {

    public static ExerciseDto of(Exercise exercise, Duration exerciseTime,
        boolean isActive) {
        return new ExerciseDto(
            exercise.getExerciseId(),
            exercise.getExerciseName(),
            exerciseTime,
            isActive
        );
    }

    public static ExerciseDto from(ExerciseHistory history) {
        return of(history.getExercise(), history.getExerciseHistoryTime(), false);
    }

    public static ExerciseDto from(ExerciseTime exerciseTime) {
        return of(exerciseTime.getExercise(), exerciseTime.getExerciseTime(),
            exerciseTime.isActive());
    }

}
