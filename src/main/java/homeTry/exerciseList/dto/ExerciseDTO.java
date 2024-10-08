package homeTry.exerciseList.dto;

import homeTry.exerciseList.model.entity.Exercise;
import homeTry.exerciseList.model.entity.ExerciseHistory;
import homeTry.exerciseList.model.entity.ExerciseTime;
import java.time.Duration;

public record ExerciseDTO(
    Long exerciseId,
    String exerciseName,
    Duration exerciseTime,
    boolean isActive
) {

    public static ExerciseDTO of(Exercise exercise, Duration exerciseTime,
        boolean isActive) {
        return new ExerciseDTO(
            exercise.getExerciseId(),
            exercise.getExerciseName(),
            exerciseTime,
            isActive
        );
    }

    public static ExerciseDTO from(ExerciseHistory history) {
        return of(history.getExercise(), history.getExerciseHistoryTime(), false);
    }

    public static ExerciseDTO from(ExerciseTime exerciseTime) {
        return of(exerciseTime.getExercise(), exerciseTime.getExerciseTime(),
            exerciseTime.isActive());
    }

}
