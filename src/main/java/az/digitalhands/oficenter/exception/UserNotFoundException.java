package az.digitalhands.oficenter.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String code, String message) {
        super(message);
    }

}