package homeTry.auth.exception.badRequestException;

import homeTry.auth.exception.AuthErrorType;
import homeTry.exception.BadRequestException;

public class InvalidAuthCodeException extends BadRequestException {

    public InvalidAuthCodeException() {
        super(AuthErrorType.INVALID_AUTH_CODE_EXCEPTION);
    }
}
