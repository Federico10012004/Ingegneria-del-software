package it.calcettohub.exceptions;

public class BookingNotCancelableException extends RuntimeException {
    public BookingNotCancelableException(String message) {
        super(message);
    }
}
