package homeTry.chatting.exception.badRequestException;

import homeTry.chatting.exception.ChattingErrorType;
import homeTry.common.exception.BadRequestException;

public class InvalidChattingTokenException extends BadRequestException {

    public InvalidChattingTokenException() {
        super(ChattingErrorType.INVALID_CHATTING_TOKEN_EXCEPTION);
    }
}
