package it.calcettohub.view.cli;

import it.calcettohub.exceptions.EscPressedException;

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

    protected String requestString(String message) {
        System.out.print(message);

        try {
            String input = getReader().readLine();

            if (input == null)
                return null;

            if (input.equalsIgnoreCase("esc")) {
                if (escHandler != null) {
                    escHandler.run();
                } else {
                    throw new EscPressedException();
                }
                return null; // segnala alla pagina che ESC è stato premuto
            }

            return input;

        } catch (IOException e) {
            throw new RuntimeException("Errore nella lettura dell'input: ", e);
        }
    }

    protected void printTitle(String title) {
        System.out.println("\n=== " + title.toUpperCase() + " ===");
    }

    protected void printEscInfo() {
        System.out.println("(Premi ESC per tornare indietro)");
    }

    protected void print(String message) {
        System.out.println(message);
    }

    protected int requestInt() {
        while (true) {
            String input = requestString("Scelta: ");

            if (input == null) {
                throw new EscPressedException();
            }

            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.err.println("Inserisci un numero valido oppure digita 'esc' per tornare indietro.");
            }
        }
    }

    protected void showErrorMessage(Exception e) {
        System.err.println(e.getMessage());
    }
}
