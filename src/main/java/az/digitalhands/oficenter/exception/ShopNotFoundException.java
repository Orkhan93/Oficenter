package az.digitalhands.oficenter.exception;

public class ShopNotFoundException extends RuntimeException {
    public ShopNotFoundException(String code, String message) {
        super(message);
    }
}