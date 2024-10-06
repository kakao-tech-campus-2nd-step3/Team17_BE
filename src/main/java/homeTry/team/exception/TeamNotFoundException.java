package homeTry.team.exception;

import homeTry.exception.BadRequestException;
import homeTry.exception.ErrorType;

public class TeamNotFoundException extends BadRequestException {
    public TeamNotFoundException() {
        super(TeamErrorType.TEAM_NOT_FOUND_EXCEPTION);
    }
}
