package homeTry.auth.exception.badRequestException;

import homeTry.auth.exception.AuthErrorType;
import homeTry.exception.BadRequestException;

public class InvalidTokenException extends BadRequestException {

    public InvalidTokenException() {
        super(AuthErrorType.INVALID_TOKEN_EXCEPTION);
    }
}
