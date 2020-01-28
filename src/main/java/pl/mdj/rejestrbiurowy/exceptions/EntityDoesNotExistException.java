package pl.mdj.rejestrbiurowy.exceptions;

public class EntityDoesNotExistException extends RuntimeException {

    public EntityDoesNotExistException() {
        super();
    }

    public EntityDoesNotExistException(String message) {
        super(message);
    }

    public EntityDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityDoesNotExistException(Throwable cause) {
        super(cause);
    }

    protected EntityDoesNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
