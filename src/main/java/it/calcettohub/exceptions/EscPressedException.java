package it.calcettohub.exceptions;

public class EscPressedException extends RuntimeException {
    public EscPressedException() {
        super("ESC handler non settato");
    }
}
