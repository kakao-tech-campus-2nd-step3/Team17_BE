package homeTry.team.exception.badRequestException;

import homeTry.common.exception.BadRequestException;
import homeTry.team.exception.TeamErrorType;

public class AlreadyJoinedTeamException extends BadRequestException {
    public AlreadyJoinedTeamException() {
        super(TeamErrorType.ALREADY_JOINED_EXCEPTION);
    }
}
