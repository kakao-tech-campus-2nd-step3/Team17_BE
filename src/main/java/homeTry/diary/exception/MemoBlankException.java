package homeTry.diary.exception;

import homeTry.exception.BadRequestException;

public class MemoBlankException extends BadRequestException{
    public MemoBlankException() {
        super(DiaryErrorType.MEMO_BLANK_EXCEPTION);
    }
}
