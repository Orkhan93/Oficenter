package az.digitalhands.oficenter.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String code, String message) {
        super(message);
    }

}