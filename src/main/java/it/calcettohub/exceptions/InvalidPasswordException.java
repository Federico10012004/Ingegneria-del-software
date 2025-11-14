package it.calcettohub.exceptions;

public class InvalidPasswordException extends Exception {
    private static final String DEFAULT_MSG = "Password errata";

    public InvalidPasswordException() {
        super(DEFAULT_MSG);
    }

    public InvalidPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPasswordException(String message) {
        super(message);
    }

    public InvalidPasswordException(Throwable cause) {
        super(DEFAULT_MSG, cause);
    }
}
