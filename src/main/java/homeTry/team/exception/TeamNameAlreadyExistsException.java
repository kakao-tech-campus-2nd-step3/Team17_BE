package homeTry.team.exception;

import homeTry.exception.BadRequestException;

public class TeamNameAlreadyExistsException extends BadRequestException {
    public TeamNameAlreadyExistsException() {
        super(TeamErrorType.TEAM_NAME_ALREADY_EXISTS_EXCEPTION);
    }
}
