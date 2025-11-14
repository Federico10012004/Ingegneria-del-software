package it.calcettohub.exceptions;

public class EmailNotFoundException extends Exception {
    private static final String DEFAULT_MSG = "Email non trovata.";

    public EmailNotFoundException() {
        super(DEFAULT_MSG);
    }

    public EmailNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailNotFoundException(String message) {
        super(message);
    }

    public EmailNotFoundException(Throwable cause) {
        super(DEFAULT_MSG, cause);
    }
}
