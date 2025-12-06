package it.calcettohub.view.cli;

import it.calcettohub.exceptions.EscPressedException;
import it.calcettohub.exceptions.SessionExpiredException;
import it.calcettohub.util.PageManager;

public class HomePageFieldManagerCli extends CliContext {

    public void start() {

        enableSessionCheck();

        printTitle("Home Field Manager");
        print("Benvenuto, cosa desideri fare?");
        print("1) Gestisci campi");
        print("2) Gestisci prenotazioni");
        print("3) Gestisci il tuo account");
        print("4) Esci");

        while (true) {
            try {
                int choice = requestIntInRange("Selezione: ", 1, 4);

                switch (choice) {
                    case 1 -> System.out.println("Gestisci campi");
                    case 2 -> System.out.println("Gestisci prenotazione");
                    case 3 -> PageManager.push(() -> new AccountCli().start());
                    case 4 -> System.exit(0);
                    default -> throw new IllegalStateException("Valore imprevisto: " + choice);
                }

                break;
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