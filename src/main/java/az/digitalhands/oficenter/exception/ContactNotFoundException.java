package az.digitalhands.oficenter.exception;

public class ContactNotFoundException extends RuntimeException {
    public ContactNotFoundException(String code, String message) {
        super(message);
    }

}