package homeTry.member.exception.internalServerException;

import homeTry.exception.InternalServerException;
import homeTry.member.exception.MemberErrorType;

public class UnknownFatalException extends InternalServerException {

    public UnknownFatalException(String message) {
        super(MemberErrorType.UNKNOWN_FATAL_EXCEPTION);

      String defaultErrorMessage = MemberErrorType.UNKNOWN_FATAL_EXCEPTION.getMessage();
      MemberErrorType.UNKNOWN_FATAL_EXCEPTION.setMessage(defaultErrorMessage + " : " + message);
    }
}
