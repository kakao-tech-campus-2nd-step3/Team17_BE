package homeTry.team.exception.badRequestException;

import homeTry.common.exception.BadRequestException;
import homeTry.team.exception.TeamErrorType;

public class TeamNameAlreadyExistsException extends BadRequestException {

    public TeamNameAlreadyExistsException() {
        super(TeamErrorType.TEAM_NAME_ALREADY_EXISTS_EXCEPTION);
    }
}
