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

        while (true) {
            try {
                int select = requestIntInRange("Selezione: ", 1, 3);

                if (select == 1) {
                    System.out.println("Gestisci campi");
                } else if (select == 2) {
                    System.out.println("Gestisci prenotazione");
                } else {
                    PageManager.push(() -> new AccountCli().start());
                }

                break;
            } catch (EscPressedException e) {
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