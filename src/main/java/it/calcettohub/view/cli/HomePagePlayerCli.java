package it.calcettohub.view.cli;

import it.calcettohub.exceptions.SessionExpiredException;
import it.calcettohub.util.PageManager;

public class HomePagePlayerCli extends CliContext {

    public void start() {

        enableSessionCheck();

        printTitle("Home Player");
        print("Benvenuto, cosa desideri fare?");
        print("1) Prenota campo");
        print("2) Organizza nuova partita");
        print("3) Gestisci il tuo account");

        while (true) {
            try {
                int select = requestIntInRange("Selezione: ", 1, 3);

                if (select == 1) {
                    System.out.println("Prenota campo");
                } else if (select == 2) {
                    System.out.println("Organizza partita");
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