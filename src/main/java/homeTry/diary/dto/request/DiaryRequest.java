package homeTry.diary.dto.request;

import com.fasterxml.jackson.annotation.JsonValue;

public record DiaryRequest(String memo) {

    @JsonValue
    public String memo() {
        return memo;
    }
}
