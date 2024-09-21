package homeTry.exerciseList.dto;

import homeTry.exerciseList.model.vo.ExerciseName;

public record ExerciseListRequest(String exerciseName) {

    public ExerciseName toExerciseName() {
        return new ExerciseName(this.exerciseName);
    }

    public static ExerciseListRequest of(String exerciseName) {
        return new ExerciseListRequest(exerciseName);
    }

}
