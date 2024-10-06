package homeTry.tag.exeception;

import homeTry.exception.BadRequestException;

public class TagNotFoundException extends BadRequestException {
    public TagNotFoundException(){
        super(TagErrorType.TAG_NOT_FOUND_EXCEPTION);
    }
}
