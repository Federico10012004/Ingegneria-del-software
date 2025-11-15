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


        int role = requestInt();

        if (role == 1) {
            AppContext.setSelectedRole(Role.PLAYER);
        } else {
            AppContext.setSelectedRole(Role.FIELDMANAGER);
        }
        PageManager.push(()->new LoginCli().login());
    }
}
