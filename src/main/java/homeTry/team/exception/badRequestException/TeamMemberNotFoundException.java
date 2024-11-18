package homeTry.team.exception.badRequestException;

import homeTry.common.exception.InternalServerException;
import homeTry.team.exception.TeamErrorType;

public class TeamMemberNotFoundException extends InternalServerException {

    public TeamMemberNotFoundException() {
        super(TeamErrorType.TEAMMEMBER_NOT_FOUND_EXCEPTION);
    }
}
