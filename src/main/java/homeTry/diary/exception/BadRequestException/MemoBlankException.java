package homeTry.diary.exception.BadRequestException;

import homeTry.diary.exception.DiaryErrorType;
import homeTry.exception.BadRequestException;

public class MemoBlankException extends BadRequestException{
    public MemoBlankException() {
        super(DiaryErrorType.MEMO_BLANK_EXCEPTION);
    }
}
