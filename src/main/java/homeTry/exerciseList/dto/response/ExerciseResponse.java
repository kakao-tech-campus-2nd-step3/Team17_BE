package homeTry.exerciseList.dto.response;

import homeTry.exerciseList.model.entity.Exercise;
import homeTry.exerciseList.model.entity.ExerciseHistory;
import homeTry.exerciseList.model.entity.ExerciseTime;

import java.time.Duration;
import java.time.LocalDateTime;

public record ExerciseResponse(
    Long exerciseId,
    String exerciseName,
    Long exerciseTime,
    boolean isActive,
    LocalDateTime startTime
) {

    public static ExerciseResponse of(Exercise exercise, Duration exerciseTime, boolean isActive,
        LocalDateTime startTime) {
        return new ExerciseResponse(
            exercise.getExerciseId(),
            exercise.getExerciseName(),
            exerciseTime.toMillis(),
            isActive,
            startTime
        );
    }

    public static ExerciseResponse from(ExerciseHistory history) {
        return of(history.getExercise(), history.getExerciseHistoryTime(), false,
            history.getExercisedAt());
    }

    public static ExerciseResponse from(ExerciseTime exerciseTime) {
        return of(exerciseTime.getExercise(), exerciseTime.getExerciseTime(),
            exerciseTime.isActive(), exerciseTime.getStartTime());
    }

}
