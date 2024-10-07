package homeTry.common.exception;

public class BadRequestException extends RuntimeException {

    private final ErrorType errorType;

    public BadRequestException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }


}
