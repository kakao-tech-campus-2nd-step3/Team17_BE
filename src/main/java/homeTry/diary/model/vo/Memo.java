package homeTry.diary.model.vo;

import jakarta.persistence.Embeddable;

import homeTry.diary.exception.MemoBlankException;


@Embeddable
public record Memo(String value) {

    public Memo {
        validateMemo(value);  
    }

    private static void validateMemo(String value) {
        if (value != null && value.isBlank())
            throw new MemoBlankException();
    }

    @Override
    public String toString() {
        return value;
    }
}
