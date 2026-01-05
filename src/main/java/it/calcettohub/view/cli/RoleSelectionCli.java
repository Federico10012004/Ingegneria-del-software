package it.calcettohub.view.cli;

import it.calcettohub.exceptions.EscPressedException;
import it.calcettohub.exceptions.UnexpectedRoleException;
import it.calcettohub.model.Role;
import it.calcettohub.utils.AppContext;
import it.calcettohub.utils.PageManager;

public class RoleSelectionCli extends CliContext {

    public void start() {

        disableSessionCheck();

        setEscHandler(()-> {
            print("Uscita dall'app ...");
            System.exit(0);
        });

        while (true) {
            print("Benvenuto in CalcettoHub");
            showMenu("Giocatore",
                    "Gestore Campo",
                    "Arbitro");
            print("Digita esc per uscire");

            try {
                int role = requestIntInRange("Scelta: ", 1, 3);

                clearScreen();
                boolean account = requestBoolean("Hai già un account (s/n)? ");

                switch (role) {
                    case 1 -> AppContext.setSelectedRole(Role.PLAYER);
                    case 2 -> AppContext.setSelectedRole(Role.FIELDMANAGER);
                    case 3 -> {
                        print("Funzionalità non ancora implementata.");
                        requestString("Premi INVIO per tornare alla selezione ruolo");
                        clearScreen();
                        continue;
                    }
                    default -> throw new UnexpectedRoleException("Ruolo inatteso");
                }

                clearScreen();
                if (!account) {
                    switch (role) {
                        case 1 -> PageManager.push(()->new PlayerRegistrationCli().playerRegistration());
                        case 2 -> PageManager.push(()->new FieldManagerRegistrationCli().fieldManagerRegistration());
                        default -> throw new UnexpectedRoleException("Ruolo inatteso");
                    }
                }

                PageManager.push(()->new LoginCli().login());
                return;
            } catch (IllegalArgumentException e) {
                showExceptionMessage(e);
            }  catch (EscPressedException _) {
                return;
            }
        }
    }
}
