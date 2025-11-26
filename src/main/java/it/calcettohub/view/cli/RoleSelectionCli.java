package it.calcettohub.view.cli;

import it.calcettohub.model.Role;
import it.calcettohub.util.AppContext;
import it.calcettohub.util.PageManager;

public class RoleSelectionCli extends CliContext {

    public void start() {

        setEscHandler(()-> {
            print("Uscita dall'app ...");
            System.exit(0);
        });

        print("Benvenuto in CalcettoHub");
        print("Scegli il tuo ruolo");
        print("1) Giocatore");
        print("2) Gestore campo");
        print("Digita esc per uscire");

        while (true) {
            try {
                int role = requestIntInRange("Scelta: ", 1, 2);
                boolean account = requestBoolean("Hai già un account(s/n): ");

                if (role == 1) {
                    AppContext.setSelectedRole(Role.PLAYER);
                } else {
                    AppContext.setSelectedRole(Role.FIELDMANAGER);
                }

                clearScreen();
                if (!account) {
                    if (role == 1) {
                        PageManager.push(()->new PlayerRegistrationCli().playerRegistration());
                    } else {
                        PageManager.push(()->new FieldManagerRegistrationCli().fieldManagerRegistration());
                    }
                }

                PageManager.push(()->new LoginCli().login());
                break;
            } catch (IllegalArgumentException e) {
                showExceptionMessage(e);
            }
        }
    }
}
