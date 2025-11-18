package it.calcettohub.view.cli;

import it.calcettohub.exceptions.EscPressedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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

    protected void printTitle(String title) {
        System.out.println("\n=== " + title.toUpperCase() + " ===");
    }

    protected void printEscInfo() {
        System.out.println("(Premi ESC per tornare indietro)");
    }

    protected void print(String message) {
        System.out.println(message);
    }

    protected String requestString(String message) {
        System.out.print(message);

        try {
            String input = getReader().readLine();

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
            throw new IllegalArgumentException("Errore nella lettura dell'input: ", e);
        }
    }

    protected int requestInt(String message) {
        return requestIntInRange(message, null, null);
    }

    protected void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    protected int requestIntInRange(String message, Integer min, Integer max) {
        String input = requestString(message);

        if (input == null) {
            throw new EscPressedException();
        }

        try {
            int value = Integer.parseInt(input);

            if (min != null && value < min) {
                throw new IllegalArgumentException("Il numero inserito deve essere >= " + min + ".");
            }
            if (max != null && value > max) {
                throw new IllegalArgumentException("Il numero inserito deve essere <= " + max + ".");
            }

            return value;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Inserisci un numero valido oppure digita 'esc' per tornare indietro.");
        }
    }

    protected boolean requestBoolean(String message) {
        String input = requestString(message);

        switch (input) {
            case "s" -> {
                return true;
            }
            case "n" -> {
                return false;
            }
            default -> throw new IllegalArgumentException("Inserire soltanto s o n.");
        }
    }

    protected LocalDate requestDate(String message) throws DateTimeParseException {
        String input = requestString(message);

        DateTimeFormatter italian = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try {
            return LocalDate.parse(input, italian);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Questa input non rappresenta una data valida. Usare il formato gg-MM-aaaa");
        }
    }

    protected void showErrorMessage(String message) {
        System.err.println(message);
    }

    protected void showExceptionMessage(Exception e) {
        System.err.println(e.getMessage());
        System.err.flush();
        System.out.println();
    }
}
