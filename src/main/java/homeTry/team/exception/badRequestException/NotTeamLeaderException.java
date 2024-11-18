package homeTry.team.exception.badRequestException;

import homeTry.common.exception.BadRequestException;
import homeTry.team.exception.TeamErrorType;

public class NotTeamLeaderException extends BadRequestException {

    public NotTeamLeaderException() {
        super(TeamErrorType.NOT_TEAM_LEADER_EXCEPTION);
    }
}
