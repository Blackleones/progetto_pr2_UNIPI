package twException;

public class EmptyMsgException extends RuntimeException {
    public EmptyMsgException(String message) {
        super(message);
    }

    public EmptyMsgException() {
        super();
    }
}
