package homeTry.common.auth.exception.internalServerException;

import homeTry.common.auth.exception.AuthErrorType;
import homeTry.common.exception.InternalServerException;

public class HomeTryServerException extends InternalServerException {

    public HomeTryServerException() {
        super(AuthErrorType.HOME_TRY_SERVER_EXCEPTION);
    }
}
