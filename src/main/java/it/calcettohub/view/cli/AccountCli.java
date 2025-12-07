package it.calcettohub.view.cli;

import it.calcettohub.exceptions.SessionExpiredException;
import it.calcettohub.model.User;
import it.calcettohub.util.PageManager;
import it.calcettohub.util.SessionManager;

public class AccountCli extends CliContext {

    public void start() {

        enableSessionCheck();

        setEscHandler(() -> {
            clearScreen();
            PageManager.pop();
        });

        while (true) {

            User user = SessionManager.getInstance().getLoggedUser();

            printTitle("Il tuo account");
            print("Ciao " + user.getEmail());
            print("1) Modifica dati account");
            print("2) Effettua logout");

            try {
                int choice = requestIntInRange("Selezione: ", 1, 2);

                switch (choice) {
                    case 1 -> PageManager.push(() -> new AccountEditCli().modify(user));
                    case 2 -> {
                        boolean logout = requestBoolean("Sei sicuro di voler effettuare il logout (s/n)? ");
                        if (logout) {
                            SessionManager.getInstance().closeSession();
                            clearScreen();
                            print("Logout effettuato");
                            PageManager.push(() -> new LoginCli().login());
                            return;
                        }
                    }
                    default -> throw new IllegalStateException("Valore imprevisto: " + choice);
                }
            } catch (SessionExpiredException e) {
                showExceptionMessage(e);
                PageManager.push(() -> new LoginCli().login());
                return;
            } catch (IllegalArgumentException e) {
                showExceptionMessage(e);
            }
        }
    }
}