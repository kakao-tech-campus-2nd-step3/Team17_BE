package homeTry.exception.serverException;

public class InternalServerException extends RuntimeException {
    InternalServerException() { super(); }
    public InternalServerException(String message) { super(message); }
}