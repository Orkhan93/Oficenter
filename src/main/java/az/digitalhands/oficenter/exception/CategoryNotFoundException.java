package az.digitalhands.oficenter.exception;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(String code, String message) {
        super(message);
    }

}