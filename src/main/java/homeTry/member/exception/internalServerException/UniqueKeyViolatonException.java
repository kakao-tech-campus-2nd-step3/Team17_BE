package homeTry.member.exception.internalServerException;

import homeTry.common.exception.InternalServerException;
import homeTry.member.exception.MemberErrorType;

public class UniqueKeyViolatonException extends InternalServerException {

    public UniqueKeyViolatonException() {
        super(MemberErrorType.DB_UNIQUE_KEY_VIOLATON_EXCEPTION);
    }
}
