package homeTry.team.exception.badRequestException;

import homeTry.common.exception.BadRequestException;
import homeTry.team.exception.TeamErrorType;

public class TeamParticipantsFullException extends BadRequestException {
    public TeamParticipantsFullException() {
        super(TeamErrorType.TEAM_PARTICIPANTS_FULLED_EXCEPTION);
    }
}
