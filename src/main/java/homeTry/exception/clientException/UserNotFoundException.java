package homeTry.exception.clientException;

public class UserNotFoundException extends BadRequestException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
