package az.digitalhands.oficenter.exception;

public class AboutNotFoundException extends RuntimeException {

    public AboutNotFoundException(String code, String message) {
        super(message);
    }
}
