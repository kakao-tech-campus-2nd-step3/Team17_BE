package homeTry.diary.model.vo;

import com.fasterxml.jackson.annotation.JsonValue;

public class Memo {
    private final String memo;

    public Memo(String memo) {
        //constraints
        this.memo = memo;
    }

    @JsonValue
    public String getMemo() {
        return memo;
    }
}
