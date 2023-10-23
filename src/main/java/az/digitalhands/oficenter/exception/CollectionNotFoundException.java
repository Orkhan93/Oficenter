package az.digitalhands.oficenter.exception;

public class CollectionNotFoundException extends RuntimeException {

    public CollectionNotFoundException(String code, String message) {
        super(message);
    }

}