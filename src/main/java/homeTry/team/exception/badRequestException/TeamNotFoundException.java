package homeTry.team.exception.badRequestException;

import homeTry.common.exception.BadRequestException;
import homeTry.team.exception.TeamErrorType;

public class TeamNotFoundException extends BadRequestException {

    public TeamNotFoundException() {
        super(TeamErrorType.TEAM_NOT_FOUND_EXCEPTION);
    }
}
