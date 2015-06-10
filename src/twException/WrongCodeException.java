package twException;

public class WrongCodeException extends RuntimeException {
    public WrongCodeException() {
        super();
    }

    public WrongCodeException(String message) {
        super(message);
    }
}
