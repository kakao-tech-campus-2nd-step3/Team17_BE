package homeTry.team.exception;

import homeTry.common.exception.BadRequestException;

public class TeamNotFoundException extends BadRequestException {

    public TeamNotFoundException() {
        super(TeamErrorType.TEAM_NOT_FOUND_EXCEPTION);
    }
}
