package homeTry.product.exception.badRequestException;

import homeTry.common.exception.BadRequestException;
import homeTry.product.exception.ProductErrorType;

public class ProductNotFoundException extends BadRequestException {

    public ProductNotFoundException() {
        super(ProductErrorType.PRODUCT_NOT_FOUND_EXCEPTION);
    }

}
