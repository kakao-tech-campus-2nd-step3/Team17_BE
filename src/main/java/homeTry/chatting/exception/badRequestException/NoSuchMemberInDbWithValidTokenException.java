package homeTry.chatting.exception.badRequestException;

import homeTry.chatting.exception.ChattingErrorType;
import homeTry.common.exception.BadRequestException;

public class NoSuchMemberInDbWithValidTokenException extends BadRequestException {

    public NoSuchMemberInDbWithValidTokenException() {
        super(ChattingErrorType.NO_SUCH_MEMBER_IN_DB_WITH_VALID_TOKEN);
    }
}
