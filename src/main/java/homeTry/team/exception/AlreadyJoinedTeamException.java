package homeTry.team.exception;

import homeTry.common.exception.BadRequestException;

public class AlreadyJoinedTeamException extends BadRequestException {
    public AlreadyJoinedTeamException() {
        super(TeamErrorType.ALREADY_JOINED_EXCEPTION);
    }
}
