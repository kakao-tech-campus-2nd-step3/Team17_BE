package homeTry.exception.clientException;

public class BadRequestException extends RuntimeException {

    BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super(message);
    }
}
