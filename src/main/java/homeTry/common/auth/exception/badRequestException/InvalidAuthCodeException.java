package homeTry.common.auth.exception.badRequestException;

import homeTry.common.auth.exception.AuthErrorType;
import homeTry.common.exception.BadRequestException;

public class InvalidAuthCodeException extends BadRequestException {

    public InvalidAuthCodeException() {
        super(AuthErrorType.INVALID_AUTH_CODE_EXCEPTION);
    }
}
