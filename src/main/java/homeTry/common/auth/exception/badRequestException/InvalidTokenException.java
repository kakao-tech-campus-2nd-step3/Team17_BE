package homeTry.common.auth.exception.badRequestException;

import homeTry.common.auth.exception.AuthErrorType;
import homeTry.common.exception.BadRequestException;

public class InvalidTokenException extends BadRequestException {

    public InvalidTokenException() {
        super(AuthErrorType.INVALID_TOKEN_EXCEPTION);
    }
}
