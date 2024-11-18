package homeTry.team.exception.badRequestException;

import homeTry.common.exception.BadRequestException;
import homeTry.team.exception.TeamErrorType;

public class TeamLeaderCannotWithdrawException extends BadRequestException {
    public TeamLeaderCannotWithdrawException() {
        super(TeamErrorType.TEAM_LEADER_CANNOT_WITHDRAW_EXCEPTION);
    }
}
