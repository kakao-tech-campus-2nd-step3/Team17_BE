package homeTry.member.exception.badRequestException;

import homeTry.exception.BadRequestException;
import homeTry.member.exception.MemberErrorType;

public class BadArgumentException extends BadRequestException {

    public BadArgumentException(String message) {
        super(MemberErrorType.BAD_ARGUMENT_EXCEPTION);

        String defaultErrorMessage = MemberErrorType.BAD_ARGUMENT_EXCEPTION.getMessage();
        MemberErrorType.BAD_ARGUMENT_EXCEPTION.setMessage(defaultErrorMessage + " : " + message);
    }

}