package it.calcettohub.view.cli;

import it.calcettohub.exceptions.EscPressedException;
import it.calcettohub.exceptions.SessionExpiredException;
import it.calcettohub.util.PageManager;

public class HomePagePlayerCli extends CliContext {

    public void start() {

        enableSessionCheck();

        printTitle("Home Player");
        print("Benvenuto, cosa desideri fare?");
        showMenu(
                "Prenota campo",
                "Organizza nuova partita",
                "Gestisci il tuo account",
                "Esci"
        );

        while (true) {
            try {
                int choice = requestIntInRange("Selezione: ", 1, 4);

                switch (choice) {
                    case 1 -> System.out.println("Prenota campo");
                    case 2 -> System.out.println("Organizza partita");
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