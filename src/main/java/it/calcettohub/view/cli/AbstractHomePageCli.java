package it.calcettohub.view.cli;

import it.calcettohub.exceptions.EscPressedException;
import it.calcettohub.exceptions.SessionExpiredException;
import it.calcettohub.util.PageManager;

public abstract class AbstractHomePageCli extends CliContext {

    protected abstract String getHomeTitle();

    protected abstract String[] getSpecificOption();

    protected abstract void onFirstOption();

    protected abstract void onSecondOption();

    public void start() {

        enableSessionCheck();

        printTitle(getHomeTitle());
        print("Benvenuto, cosa desideri fare?");

        String[] specificOption = getSpecificOption();

        showMenu(
                specificOption[0],
                specificOption[1],
                "Gestisci il tuo account",
                "Esci"
        );

        while (true) {
            try {
                int choice = requestIntInRange("Selezione: ", 1, 4);

                clearScreen();

                switch (choice) {
                    case 1 -> onFirstOption();
                    case 2 -> onSecondOption();
                    case 3 -> PageManager.push(() -> new AccountCli().start());
                    case 4 -> System.exit(0);
                    default -> throw new IllegalStateException("Valore imprevisto: " + choice);
                }
            } catch (EscPressedException _) {
                showErrorMessage("Esc non disponibile in questa pagina.");
            } catch (SessionExpiredException e) {
                showExceptionMessage(e);
                PageManager.pop();
                return;
            } catch (IllegalArgumentException e) {
                showExceptionMessage(e);
            }
        }
    }
}