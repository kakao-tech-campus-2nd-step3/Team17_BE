package homeTry.product.exception.badRequestException;

import homeTry.common.exception.BadRequestException;
import homeTry.product.exception.ProductErrorType;

public class InvalidMemberException extends BadRequestException {

    public InvalidMemberException() {
        super(ProductErrorType.INVALID_MEMBER_EXCEPTION);
    }

}
