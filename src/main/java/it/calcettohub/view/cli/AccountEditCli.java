package it.calcettohub.view.cli;

import it.calcettohub.bean.AccountBean;
import it.calcettohub.bean.FieldManagerAccountBean;
import it.calcettohub.bean.PlayerAccountBean;
import it.calcettohub.controller.AccountController;
import it.calcettohub.exceptions.EscPressedException;
import it.calcettohub.exceptions.SessionExpiredException;
import it.calcettohub.model.*;
import it.calcettohub.utils.PageManager;

public class AccountEditCli extends CliContext {
    private final AccountController controller = new AccountController();

    public void modify(User user) {

        enableSessionCheck();

        setEscHandler(() -> {
            clearScreen();
            PageManager.pop();
        });

        Role role = user.getRole();

        printTitle("Modifica dati account");
        printEscInfo();

        AccountBean bean = (role == Role.PLAYER) ? new PlayerAccountBean() : new FieldManagerAccountBean();

        while (true) {

            showOption(role);

            try {
                int choice = requestIntInRange("Selezione: ", 0, 4);

                switch (choice) {
                    case 1 -> validateBeanField(()-> bean.setName(requestString("Nome: ")));
                    case 2 -> validateBeanField(()-> bean.setSurname(requestString("Cognome: ")));
                    case 3 -> {
                        validateBeanField(()-> bean.setPassword(requestString("Nuova password: ")));
                        validateBeanField(()-> bean.setConfirmPassword(requestString("Conferma nuova password: ")));
                        controller.updateUserPassword(bean);
                        clearScreen();
                        print("Password modificata con successo");
                    }
                    case 4 -> updateRoleSpecificField(bean);
                    case 0 -> {
                        controller.updateUserData(bean);
                        clearScreen();
                        print("Modifiche applicate con successo.");
                        PageManager.pop();
                        return;
                    }
                    default -> throw new IllegalStateException("Valore imprevisto: " + choice);
                }
            } catch (EscPressedException _) {
                return;
            } catch (SessionExpiredException e) {
                showExceptionMessage(e);
                expiredSession();
                return;
            } catch (IllegalArgumentException e) {
                showExceptionMessage(e);
            }
        }
    }

    private void updateRoleSpecificField(AccountBean bean) {
        if (bean instanceof PlayerAccountBean p) {
            validateBeanField(()-> p.setPosition(PlayerPosition.fromString(requestString("Nuova posizione preferita (portiere, difensore, centrocampista, attaccante): "))));
        }
        if (bean instanceof FieldManagerAccountBean f){
            validateBeanField(()-> f.setPhoneNumber(requestString("Nuovo numero di telefono: ")));
        }
    }

    private void showOption(Role role) {

        if (role == Role.PLAYER) {
            showMenu(
                    "Modifica nome",
                    "Modifica cognome",
                    "Modifica password",
                    "Modifica posizione preferita"
            );
        } else {
            showMenu(
                    "Modifica nome",
                    "Modifica cognome",
                    "Modifica password",
                    "Modifica numero di telefono"
            );
        }

        print("Digita 0 per applicare le modifiche");
    }
}