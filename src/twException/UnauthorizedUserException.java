package twException;

public class UnauthorizedUserException extends RuntimeException {
    public UnauthorizedUserException() {
        super();
    }

    public UnauthorizedUserException(String message) {
        super(message);
    }
}
