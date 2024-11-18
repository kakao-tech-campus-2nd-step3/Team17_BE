package homeTry.tag.productTag.exception.badRequestException;

import homeTry.common.exception.BadRequestException;
import homeTry.tag.productTag.exception.ProductTagErrorType;

public class ProductTagAlreadyExistsException extends BadRequestException {

    public ProductTagAlreadyExistsException() {super(ProductTagErrorType.PRODUCT_TAG_ALREADY_EXISTS_EXCEPTION);}
}
