package homeTry.tag.exception.badRequestException;

import homeTry.common.exception.BadRequestException;
import homeTry.tag.exception.TagErrorType;

public class ForbiddenTagAccessException extends BadRequestException {
    public ForbiddenTagAccessException() {super(TagErrorType.FORBIDDEN_TAG_ACCESS_EXCEPTION);}
}
