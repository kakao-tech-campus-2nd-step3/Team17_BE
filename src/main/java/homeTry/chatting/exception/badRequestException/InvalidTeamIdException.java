package homeTry.chatting.exception.badRequestException;

import homeTry.chatting.exception.ChattingErrorType;
import homeTry.common.exception.BadRequestException;

public class InvalidTeamIdException extends BadRequestException {

    public InvalidTeamIdException(){ super(ChattingErrorType.INVALID_TEAM_ID_EXCEPTION); }
}
