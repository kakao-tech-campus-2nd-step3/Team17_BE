package homeTry.member.exception.badRequestException;

import homeTry.exception.BadRequestException;
import homeTry.member.exception.MemberErrorType;

public class LoginFailedException extends BadRequestException {
    public LoginFailedException() { super(MemberErrorType.LOGIN_FAILED_EXCEPTION); }
}
