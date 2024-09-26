package homeTry.exerciseList.dto;

import homeTry.exerciseList.model.vo.ExerciseName;

public record ExerciseRequest(String exerciseName) {

    public ExerciseName toExerciseName() {
        return new ExerciseName(this.exerciseName);
    }

}
