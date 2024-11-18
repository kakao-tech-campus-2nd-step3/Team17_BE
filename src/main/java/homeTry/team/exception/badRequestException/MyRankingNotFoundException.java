package homeTry.team.exception.badRequestException;

import homeTry.common.exception.BadRequestException;
import homeTry.team.exception.TeamErrorType;

public class MyRankingNotFoundException extends BadRequestException {
    public MyRankingNotFoundException() {
        super(TeamErrorType.MY_RANKING_NOT_FOUND_EXCEPTION);
    }
}
