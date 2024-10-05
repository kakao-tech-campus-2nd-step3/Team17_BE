package homeTry.exerciseList.model.vo;

import homeTry.exerciseList.exception.badRequestException.ExerciseNameBlankException;
import homeTry.exerciseList.exception.badRequestException.ExerciseNameTooLongException;
import jakarta.persistence.Embeddable;

@Embeddable
public record ExerciseName(String value) {

    public ExerciseName {
        validateExerciseName(value);
    }

    private void validateExerciseName(String exerciseName) {
        // 운동 이름 공백 불가
        if (exerciseName == null || exerciseName.isBlank()) {
            throw new ExerciseNameBlankException();
        }

        // 운동 이름은 최대 20자 까지
        if (exerciseName.length() > 20) {
            throw new ExerciseNameTooLongException();
        }

    }

    @Override
    public String toString() {
        return value;
    }

}
