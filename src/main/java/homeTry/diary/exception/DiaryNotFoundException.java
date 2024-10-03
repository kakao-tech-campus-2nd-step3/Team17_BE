package homeTry.diary.exception;

import homeTry.exception.BadRequestException;

public class DiaryNotFoundException extends BadRequestException{
    public DiaryNotFoundException(){
        super(DiaryErrorType.DIARY_NOT_FOUND_EXCEPTION);
    }
}
