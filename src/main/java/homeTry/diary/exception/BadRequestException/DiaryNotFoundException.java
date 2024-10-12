package homeTry.diary.exception.BadRequestException;

import homeTry.common.exception.BadRequestException;
import homeTry.diary.exception.DiaryErrorType;

public class DiaryNotFoundException extends BadRequestException {

    public DiaryNotFoundException() {
        super(DiaryErrorType.DIARY_NOT_FOUND_EXCEPTION);
    }
}
