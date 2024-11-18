package homeTry.admin.exception.badReqeustException;

import homeTry.admin.exception.AdminErrorType;
import homeTry.common.exception.BadRequestException;

public class InvalidAdminCodeException extends BadRequestException {

    public InvalidAdminCodeException() {
        super(AdminErrorType.INVALID_ADMIN_CODE_EXCEPTION);
    }
}
