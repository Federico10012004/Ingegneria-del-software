/*package it.calcettohub.view.cli;

import it.calcettohub.bean.FieldManagerAccountBean;
import it.calcettohub.bean.PlayerAccountBean;
import it.calcettohub.model.Role;
import it.calcettohub.util.PageManager;
import it.calcettohub.util.Session;
import it.calcettohub.util.SessionManager;

public class AccountEditCli extends CliContext {

    public void modify(Role role) {

        setEscHandler(() -> {
            clearScreen();
            PageManager.pop();
        });

        printTitle("Modifica dati account");
        printEscInfo();

        while (true) {

            print("1) Modifica nome");
            print("2) Modifica cognome");
            print("3) Modifica data di nascita");
            print("4) Modifica password");

            if (role == Role.PLAYER) {
                print("5) Modifica posizione preferita");
                PlayerAccountBean bean = new PlayerAccountBean();
            } else {
                print("5) Modifica numero di telefono");
                FieldManagerAccountBean bean = new FieldManagerAccountBean();
            }

            Session session = SessionManager.getInstance().getCurrentSession();
            if (session == null) {
                showErrorMessage("Sessione scaduta, reindirizzamento al login ...");
                PageManager.push(()->new LoginCli().login());
                return;
            }

            try {
                int choice = requestIntInRange("Selezione: ", 1, 5);

                switch (choice) {
                    case 1 ->
                }
            }
        }
    }
}*/
