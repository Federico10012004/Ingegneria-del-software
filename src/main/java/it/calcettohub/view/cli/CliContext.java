package it.calcettohub.view.cli;

import it.calcettohub.exceptions.EscPressedException;
import it.calcettohub.exceptions.SessionExpiredException;
import it.calcettohub.utils.PageManager;
import it.calcettohub.utils.Session;
import it.calcettohub.utils.SessionManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public abstract class CliContext {
    private static BufferedReader reader;
    private Runnable escHandler;
    private boolean sessionCheckEnabled = false;

    protected static synchronized BufferedReader getReader() {
        if (reader == null) {
            reader = new BufferedReader(new InputStreamReader(System.in));
        }

        return reader;
    }

    protected void enableSessionCheck() {
        this.sessionCheckEnabled = true;
    }

    protected void disableSessionCheck() {
        this.sessionCheckEnabled = false;
    }

    protected void setEscHandler(Runnable handler) {
        escHandler = handler;
    }

    protected void printTitle(String title) {
        System.out.println("\n=== " + title.toUpperCase() + " ===");
    }

    protected void printEscInfo() {
        System.out.println("(Digita esc per tornare indietro)");
    }

    protected void print(String message) {
        System.out.println(message);
    }

    protected String requestString(String message) {
        System.out.print(message);

        try {
            String input = getReader().readLine().trim();

            if (input.equalsIgnoreCase("esc")) {
                if (escHandler != null) {
                    escHandler.run();
                }

                throw new EscPressedException();
            }

            checkSession();

            return input;

        } catch (IOException e) {
            throw new IllegalArgumentException("Errore nella lettura dell'input: ", e);
        }
    }

    protected void validateBeanField(Runnable setter) {
        while (true) {
            try {
                setter.run();
                break;
            } catch (IllegalArgumentException e) {
                showExceptionMessage(e);
            }
        }
    }

    protected int requestInt(String message) {
        return requestIntInRange(message, null, null);
    }

    protected void clearScreen() {
        System.out.print("\033[H\033[2J");
    }

    protected int requestIntInRange(String message, Integer min, Integer max) {
        String input = requestString(message);

        try {
            int value = Integer.parseInt(input);

            if (min != null && value < min) {
                throw new IllegalArgumentException("Il numero inserito deve essere >= " + min + ".");
            }
            if (max != null && value > max) {
                throw new IllegalArgumentException("Il numero inserito deve essere <= " + max + ".");
            }

            return value;
        } catch (NumberFormatException _) {
            throw new IllegalArgumentException("Inserisci un numero valido.");
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
        } catch (DateTimeParseException _) {
            throw new IllegalArgumentException("Questo input non rappresenta una data valida. Usare il formato gg-MM-aaaa");
        }
    }

    protected void showErrorMessage(String message) {
        System.err.println(message);
    }

    protected void showExceptionMessage(Exception e) {
        System.err.println(e.getMessage());
        System.out.println();
    }

    protected void checkSession() {
        if (sessionCheckEnabled) {
            Session session = SessionManager.getInstance().getCurrentSession();
            if (session == null) {
                throw new SessionExpiredException();
            }
        }
    }

    protected void expiredSession() {
        PageManager.clear();
        PageManager.pushSilent(() -> new RoleSelectionCli().start());
        PageManager.push(() -> new LoginCli().login());
    }

    protected void showMenu(String ... options) {
        int num = 1;
        for (String option : options) {
            print(num + ") " + option);
            num++;
        }
    }
}
