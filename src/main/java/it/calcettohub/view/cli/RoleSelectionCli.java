package it.calcettohub.view.cli;

import it.calcettohub.model.Role;
import it.calcettohub.util.AppContext;

import java.io.IOException;

public class RoleSelectionCli extends CliContext {

    public void start() {

        System.out.println("Benvenuto in CalcettoHub");
        System.out.println("Scegli il tuo ruolo");
        System.out.println("1) Giocatore");
        System.out.println("2) Gestore campo");

        try {
            int role = Integer.parseInt(requestString());

            if (role == 1) {
                AppContext.setSelectedRole(Role.PLAYER);
            } else {
                AppContext.setSelectedRole(Role.FIELDMANAGER);
            }

            new LoginCli().login();
        } catch (IOException _) {

        }
    }
}
