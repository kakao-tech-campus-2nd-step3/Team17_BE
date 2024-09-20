package homeTry.exerciseList.model.vo;

import java.util.Objects;
import jakarta.persistence.Embeddable;

@Embeddable
public class ExerciseName {

    private String value;

    protected ExerciseName() {
    }

    public ExerciseName(String value) {
        validateExerciseName(value);
        this.value = value;
    }

    private void validateExerciseName(String exerciseName) {
        if (exerciseName == null || exerciseName.isBlank()) {
            throw new IllegalArgumentException("운동 이름은 필수입니다.");
        }
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof ExerciseName)) {
            return false;
        }

        ExerciseName exerciseName = (ExerciseName) object;
        return Objects.equals(this.value, exerciseName.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }

}
