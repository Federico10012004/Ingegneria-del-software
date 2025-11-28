package it.calcettohub.exceptions;

public class SessionExpiredException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "Sessione scaduta. Reindirizzamento al login ...";

    public SessionExpiredException() {
        super(DEFAULT_MESSAGE);
    }

    public SessionExpiredException(String message) {
        super(message);
    }

    public SessionExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public SessionExpiredException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }
}
