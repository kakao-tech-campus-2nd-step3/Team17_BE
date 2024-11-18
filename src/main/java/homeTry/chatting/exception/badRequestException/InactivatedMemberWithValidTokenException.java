package homeTry.chatting.exception.badRequestException;

import homeTry.chatting.exception.ChattingErrorType;
import homeTry.common.exception.BadRequestException;

public class InactivatedMemberWithValidTokenException extends BadRequestException {

    public InactivatedMemberWithValidTokenException() {
        super(ChattingErrorType.INACTIVATED_MEMBER_WITH_VALID_TOKEN);
    }
}
