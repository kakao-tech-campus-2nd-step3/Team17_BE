package homeTry.exerciseList.dto;

import homeTry.exerciseList.model.entity.Exercise;

public record ExerciseResponse(
    Long exerciseId,
    String exerciseName,
    Long exerciseTime,
    boolean isActive
) {

    public static ExerciseResponse fromEntity(Exercise exercise) {
        return new ExerciseResponse(
            exercise.getExerciseId(),
            exercise.getExerciseName(),
            exercise.getCurrentExerciseTime().getExerciseTime().toSeconds(),
            exercise.getCurrentExerciseTime().isActive()
        );
    }

}
