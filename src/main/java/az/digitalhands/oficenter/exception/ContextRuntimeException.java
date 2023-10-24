package az.digitalhands.oficenter.exception;

public class ContextRuntimeException extends RuntimeException {

    public ContextRuntimeException(String code, String message) {
        super(message);
    }

}