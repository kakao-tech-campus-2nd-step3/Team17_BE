package homeTry.exerciseList.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ExerciseRequest(
        @NotBlank
        @Size(min = 1, max = 20, message = "운동 이름은 최소 1글자, 최대 20글자 입니다.")
        String exerciseName) {

}
