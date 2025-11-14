package it.calcettohub.view.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class CliContext {
    private static BufferedReader reader;
    private static Runnable escHandler;

    protected static synchronized BufferedReader getReader() {
        if (reader == null) {
            reader = new BufferedReader(new InputStreamReader(System.in));
        }

        return reader;
    }

    protected static void setEscHandler(Runnable handler) {
        escHandler = handler;
    }

    protected String requestString(String message) throws IOException {
        System.out.print(message);
        String input = getReader().readLine();

        if (input == null)
            return null;

        if (input.equalsIgnoreCase("esc")) {
            if (escHandler != null) {
                escHandler.run();
            } else {
                System.out.println("ESC premuto, ma nessun handler è stato definito.");
            }
            return null; // segnala alla pagina che ESC è stato premuto
        }

        return input;
    }

    protected int

    protected void showErrorMessage(Exception e) {
        System.err.println(e.getMessage());
    }
}
