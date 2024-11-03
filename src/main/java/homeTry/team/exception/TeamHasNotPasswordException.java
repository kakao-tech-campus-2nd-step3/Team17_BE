package homeTry.team.exception;

import homeTry.common.exception.BadRequestException;

public class TeamHasNotPasswordException extends BadRequestException {
    public TeamHasNotPasswordException() {
        super(TeamErrorType.TEAM_HAS_NOT_PASSWORD_EXCEPTION);
    }
}
