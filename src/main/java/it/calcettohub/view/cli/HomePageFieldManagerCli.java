package it.calcettohub.view.cli;

import it.calcettohub.exceptions.SessionExpiredException;
import it.calcettohub.util.PageManager;
import it.calcettohub.util.Session;
import it.calcettohub.util.SessionManager;

public class HomePageFieldManagerCli extends CliContext {

    public void start() {

        printTitle("Home Field Manager");
        print("Benvenuto, cosa desideri fare?");
        print("1) Gestisci campi");
        print("2) Gestisci prenotazioni");
        print("3) Gestisci il tuo account");

        while (true) {

            Session session = SessionManager.getInstance().getCurrentSession();
            if (session == null) {
                showErrorMessage("Sessione scaduta, reindirizzamento al login ...");
                PageManager.pop();
                return;
            }

            try {
                int select = requestIntInRange("Selezione: ", 1, 3);

                if (select == 1) {
                    System.out.println("Gestisci campi");
                } else if (select == 2) {
                    System.out.println("Gestisci prenotazione");
                } else {
                    System.out.println("Gestione account");
                }

                break;
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