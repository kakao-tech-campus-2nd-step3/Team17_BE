package homeTry.product.exception.badRequestException;

import homeTry.common.exception.BadRequestException;
import homeTry.product.exception.ProductErrorType;

public class MissingProductTagException extends BadRequestException {

    public MissingProductTagException() {
        super(ProductErrorType.MISSING_PRODUCT_TAG_EXCEPTION);
    }

}
