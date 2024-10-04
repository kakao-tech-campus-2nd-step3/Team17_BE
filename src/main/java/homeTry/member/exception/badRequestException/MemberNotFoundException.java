package homeTry.member.exception.badRequestException;

import homeTry.exception.BadRequestException;
import homeTry.member.exception.MemberErrorType;

public class MemberNotFoundException extends BadRequestException {
    public MemberNotFoundException() { super(MemberErrorType.MEMBER_NOT_FOUND_EXCEPTION); }
}
