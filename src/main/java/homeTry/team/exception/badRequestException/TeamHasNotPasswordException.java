package homeTry.team.exception.badRequestException;

import homeTry.common.exception.BadRequestException;
import homeTry.team.exception.TeamErrorType;

public class TeamHasNotPasswordException extends BadRequestException {
    public TeamHasNotPasswordException() {
        super(TeamErrorType.TEAM_HAS_NOT_PASSWORD_EXCEPTION);
    }
}
