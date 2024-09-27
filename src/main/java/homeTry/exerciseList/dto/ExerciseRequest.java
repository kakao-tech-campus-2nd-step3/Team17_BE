package homeTry.exerciseList.dto;

import jakarta.validation.constraints.NotBlank;

public record ExerciseRequest(
    @NotBlank(message = "운동 이름은 필수입니다.")
    String exerciseName) {

}
