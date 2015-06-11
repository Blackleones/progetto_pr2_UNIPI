package twException;

public class UnauthorizedAccessException extends RuntimeException {

    public UnauthorizedAccessException(){
        super();
    }

    public UnauthorizedAccessException(String s){
        super(s);
    }
}
