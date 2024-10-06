package homeTry.diary.dto.request;

import homeTry.diary.exception.BadRequestException.MemoBlankException;
import homeTry.diary.exception.BadRequestException.MemoTooLongException;

public record DiaryRequest(String memo) {
    private static final int MAX_LENGTH = 500;

    public DiaryRequest {
        validateMemo(memo);  
    }

    private static void validateMemo(String memo) {
        if (memo != null && memo.isBlank()) {
            throw new MemoBlankException();
        }
        if (memo != null && memo.length() > MAX_LENGTH) {
            throw new MemoTooLongException();
        }
    }
}
