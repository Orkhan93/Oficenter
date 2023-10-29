package az.digitalhands.oficenter.exception;

public class CartNotFoundException extends RuntimeException {

    public CartNotFoundException(String code, String message) {
        super(message);
    }

}