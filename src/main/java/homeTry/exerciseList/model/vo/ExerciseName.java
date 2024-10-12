package homeTry.exerciseList.model.vo;

import jakarta.persistence.Embeddable;

@Embeddable
public record ExerciseName(String value) {

    public ExerciseName {
        validateExerciseName(value);
    }

    private void validateExerciseName(String exerciseName) {
        // 운동 이름 공백 불가
        if (exerciseName == null || exerciseName.isBlank()) {
            throw new IllegalArgumentException("운동 이름은 필수입니다.");
        }

        // 운동 이름은 최대 20자 까지
        if (exerciseName.length() > 20) {
            throw new IllegalArgumentException(
                    String.format("운동 이름은 최대 20자까지 가능합니다. 현재 글자 수: %d자", exerciseName.length()));
        }

    }

}
