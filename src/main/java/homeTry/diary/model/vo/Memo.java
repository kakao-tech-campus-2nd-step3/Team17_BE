package homeTry.diary.model.vo;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Embeddable
public record Memo(
        @NotBlank(message = "메모는 공백일 수 없습니다.") 
        @Size(min = 1, max = 500, message = "메모는 최소 1글자, 최대 500글자입니다.") 
        String value) {

}
