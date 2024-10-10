package homeTry.team.exception;

import homeTry.common.exception.BadRequestException;

public class MyRankingNotFoundException extends BadRequestException {
    public MyRankingNotFoundException() {
        super(TeamErrorType.MY_RANKING_NOT_FOUND_EXCEPTION);
    }
}
