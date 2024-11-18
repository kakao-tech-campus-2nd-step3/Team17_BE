package homeTry.member.exception.badRequestException;

import homeTry.common.exception.BadRequestException;
import homeTry.member.exception.MemberErrorType;

public class InactivatedMemberException extends BadRequestException {

    public InactivatedMemberException() {
        super(MemberErrorType.INACTIVATED_MEMBER_EXCEPTION);
    }
}
