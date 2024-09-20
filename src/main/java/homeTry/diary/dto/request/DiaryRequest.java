package homeTry.diary.dto.request;

import com.fasterxml.jackson.annotation.JsonValue;

import homeTry.diary.model.vo.Memo;

public class DiaryRequest {

    private Memo memo;

    @JsonValue
    public Memo getMemo() {
        return memo;
    }
}
