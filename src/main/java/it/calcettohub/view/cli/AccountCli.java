/*package it.calcettohub.view.cli;

import it.calcettohub.model.Player;
import it.calcettohub.model.User;
import it.calcettohub.util.PageManager;
import it.calcettohub.util.Session;
import it.calcettohub.util.SessionManager;

public class AccountCli extends CliContext {

    public void start() {

        setEscHandler(() -> {
            clearScreen();
            PageManager.pop();
        });

        while (true) {

            Session session = SessionManager.getInstance().getCurrentSession();
            if (session == null) {
                showErrorMessage("Sessione scaduta, reindirizzamento al login ...");
                PageManager.push(()->new LoginCli().login());
                return;
            }

            User user = session.getUser();

            printTitle("Ciao " + user.getName() + user.getSurname());
            print("1) Modifica dati account");
            print("2) Modifica password");
            print("3) Effettua logout");

            try {
                int select = requestIntInRange("Selezione: ", 1, 3);

                if (select == 1) {
                    PageManager.push(()->new AccountEditCli().modify(user.getRole()));
                } else if (select == 2) {
                    System.out.println("Modifica password");
                } else {
                    boolean logout = requestBoolean("Sei sicuro di voler effettuare il logout (s/n)? ");
                    if (logout) {
                        clearScreen();
                        print("Logout effettuato");
                        PageManager.push(()->new LoginCli().login());
                        return;
                    } else {
                        continue;
                    }
                }

                break;
            } catch (IllegalArgumentException e) {
                showExceptionMessage(e);
            }
        }
    }
}
*/