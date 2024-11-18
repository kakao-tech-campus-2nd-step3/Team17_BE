package homeTry.team.exception.badRequestException;

import homeTry.common.exception.BadRequestException;
import homeTry.team.exception.TeamErrorType;

public class InvalidPasswordException extends BadRequestException {
    public InvalidPasswordException() {
        super(TeamErrorType.INVALID_PASSWORD_EXCEPTION);
    }
}
