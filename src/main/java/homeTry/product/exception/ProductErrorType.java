package homeTry.product.exception;

import homeTry.common.exception.ErrorType;
import org.springframework.http.HttpStatus;

public enum ProductErrorType implements ErrorType {

    INVALID_MEMBER_EXCEPTION("Product400_001", HttpStatus.BAD_REQUEST, "유효하지 않은 회원입니다."),

    PRODUCT_NOT_FOUND_EXCEPTION("Product404_001", HttpStatus.NOT_FOUND, "해당 상품을 찾을 수 없습니다.");

    private final String errorCode;
    private final HttpStatus httpStatus;
    private final String message;

    ProductErrorType(String errorCode, HttpStatus httpStatus, String message) {
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        this.message = message;
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
