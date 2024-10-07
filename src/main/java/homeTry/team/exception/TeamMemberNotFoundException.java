package homeTry.team.exception;

import homeTry.common.exception.InternalServerException;

public class TeamMemberNotFoundException extends InternalServerException {

    public TeamMemberNotFoundException() {
        super(TeamErrorType.TEAMMEMBER_NOT_FOUND_EXCEPTION);
    }
}
