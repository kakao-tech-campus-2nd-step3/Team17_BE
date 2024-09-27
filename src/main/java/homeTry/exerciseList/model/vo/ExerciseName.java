package homeTry.exerciseList.model.vo;

import jakarta.persistence.Embeddable;

@Embeddable
public record ExerciseName(String value) {

    public ExerciseName {
        validateExerciseName(value);
    }

    private void validateExerciseName(String exerciseName) {
        if (exerciseName == null || exerciseName.isBlank()) {
            throw new IllegalArgumentException("운동 이름은 필수입니다.");
        }
    }

    @Override
    public String toString() {
        return value;
    }

}
