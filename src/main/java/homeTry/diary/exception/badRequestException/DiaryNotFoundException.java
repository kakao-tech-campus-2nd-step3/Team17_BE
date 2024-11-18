package homeTry.diary.exception.badRequestException;

import homeTry.common.exception.BadRequestException;
import homeTry.diary.exception.DiaryErrorType;

public class DiaryNotFoundException extends BadRequestException {

    public DiaryNotFoundException() {
        super(DiaryErrorType.DIARY_NOT_FOUND_EXCEPTION);
    }
}
