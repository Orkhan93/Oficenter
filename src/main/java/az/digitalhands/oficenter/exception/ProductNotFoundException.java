package az.digitalhands.oficenter.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String code, String message) {
        super(message);
    }

}