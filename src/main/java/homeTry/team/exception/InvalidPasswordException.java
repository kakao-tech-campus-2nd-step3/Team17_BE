package homeTry.team.exception;

import homeTry.common.exception.BadRequestException;

public class InvalidPasswordException extends BadRequestException {
    public InvalidPasswordException() {
        super(TeamErrorType.INVALID_PASSWORD_EXCEPTION);
    }
}
