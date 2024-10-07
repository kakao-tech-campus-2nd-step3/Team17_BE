package homeTry.auth.exception.internalServerException;

import homeTry.auth.exception.AuthErrorType;
import homeTry.exception.InternalServerException;

public class HomeTryServerException extends InternalServerException {

    public HomeTryServerException() {
        super(AuthErrorType.HOME_TRY_SERVER_EXCEPTION);
    }
}
