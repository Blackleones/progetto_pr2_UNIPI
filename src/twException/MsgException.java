package twException;

public class MsgException extends RuntimeException {
    public MsgException() {
        super();
    }

    public MsgException(String message) {
        super(message);
    }
}
