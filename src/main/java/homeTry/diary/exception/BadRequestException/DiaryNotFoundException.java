package homeTry.diary.exception.BadRequestException;

import homeTry.diary.exception.DiaryErrorType;
import homeTry.common.exception.BadRequestException;

public class DiaryNotFoundException extends BadRequestException {

    public DiaryNotFoundException() {
        super(DiaryErrorType.DIARY_NOT_FOUND_EXCEPTION);
    }
}
