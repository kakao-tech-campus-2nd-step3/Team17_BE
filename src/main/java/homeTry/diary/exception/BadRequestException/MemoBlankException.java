package homeTry.diary.exception.BadRequestException;

import homeTry.common.exception.BadRequestException;
import homeTry.diary.exception.DiaryErrorType;

public class MemoBlankException extends BadRequestException {

    public MemoBlankException() {
        super(DiaryErrorType.MEMO_BLANK_EXCEPTION);
    }
}
