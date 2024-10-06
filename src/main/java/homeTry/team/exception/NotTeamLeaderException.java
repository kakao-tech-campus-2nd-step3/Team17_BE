package homeTry.team.exception;

import homeTry.exception.BadRequestException;

public class NotTeamLeaderException extends BadRequestException {
    public NotTeamLeaderException(){
        super(TeamErrorType.NOT_TEAM_LEADER_EXCEPTION);
    }
}
