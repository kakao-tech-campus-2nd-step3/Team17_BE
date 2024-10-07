package homeTry.exception;

public class InternalServerException extends RuntimeException {

    private final ErrorType errorType;

    public InternalServerException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }


}
