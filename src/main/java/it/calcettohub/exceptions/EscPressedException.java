package it.calcettohub.exceptions;

public class EscPressedException extends RuntimeException {
    public EscPressedException() {
        super("ESC premuto dall'utente");
    }
}
