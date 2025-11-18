package it.calcettohub.exceptions;

public class EmailAlreadyExistsException extends Exception {
    private static final String DEFAULT_MSG = "Email già esistente.";

    public EmailAlreadyExistsException() {
        super(DEFAULT_MSG);
    }

    public EmailAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailAlreadyExistsException(String message) {
        super(message);
    }

    public EmailAlreadyExistsException(Throwable cause) {
        super(DEFAULT_MSG, cause);
    }
}
