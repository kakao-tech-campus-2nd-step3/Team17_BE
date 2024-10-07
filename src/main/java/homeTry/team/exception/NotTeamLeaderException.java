package homeTry.team.exception;

import homeTry.common.exception.BadRequestException;

public class NotTeamLeaderException extends BadRequestException {

    public NotTeamLeaderException() {
        super(TeamErrorType.NOT_TEAM_LEADER_EXCEPTION);
    }
}
