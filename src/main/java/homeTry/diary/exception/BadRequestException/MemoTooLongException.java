package homeTry.diary.exception.BadRequestException;

import homeTry.common.exception.BadRequestException;
import homeTry.diary.exception.DiaryErrorType;

public class MemoTooLongException extends BadRequestException {

    public MemoTooLongException() {
        super(DiaryErrorType.MEMO_TOO_LONG_EXCEPTION);
    }
}
