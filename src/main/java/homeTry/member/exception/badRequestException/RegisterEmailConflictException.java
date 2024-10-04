package homeTry.member.exception.badRequestException;

import homeTry.exception.BadRequestException;
import homeTry.member.exception.MemberErrorType;

public class RegisterEmailConflictException extends BadRequestException {

    public RegisterEmailConflictException() {
        super(MemberErrorType.REGISTER_EMAIL_CONFLICT_EXCEPTION);
    }
}
