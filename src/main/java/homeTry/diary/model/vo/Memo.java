package homeTry.diary.model.vo;

import homeTry.diary.exception.BadRequestException.MemoBlankException;
import homeTry.diary.exception.BadRequestException.MemoTooLongException;
import jakarta.persistence.Embeddable;

@Embeddable
public record Memo(String value) {

    private static final int MAX_LENGTH = 500;

    public Memo {
        validateMemo(value);  
    }

    private static void validateMemo(String value) {
        if (value != null && value.isBlank()) {
            throw new MemoBlankException();
        }
        if (value != null && value.length() > MAX_LENGTH) {
            throw new MemoTooLongException();
        }
    }

    @Override
    public String toString() {
        return value;
    }
}
