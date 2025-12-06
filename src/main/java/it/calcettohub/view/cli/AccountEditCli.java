package it.calcettohub.view.cli;

import it.calcettohub.bean.AccountBean;
import it.calcettohub.bean.FieldManagerAccountBean;
import it.calcettohub.bean.PlayerAccountBean;
import it.calcettohub.controller.AccountController;
import it.calcettohub.exceptions.SessionExpiredException;
import it.calcettohub.model.PlayerPosition;
import it.calcettohub.model.Role;
import it.calcettohub.util.PageManager;

import java.util.ArrayList;
import java.util.List;

public class AccountEditCli extends CliContext {
    private final AccountController controller = new AccountController();

    public void modify(Role role) {

        enableSessionCheck();

        setEscHandler(() -> {
            clearScreen();
            PageManager.pop();
        });

        printTitle("Modifica dati account");
        printEscInfo();

        AccountBean bean;

        if (role == Role.PLAYER) {
            bean = new PlayerAccountBean();
        } else {
            bean = new FieldManagerAccountBean();
        }

        while (true) {

            showOption(role);

            try {
                int choice = requestIntInRange("Selezione: ", 0, 5);

                switch (choice) {
                    case 1 -> validateBeanField(()-> bean.setName(requestString("Nome: ")));
                    case 2 -> validateBeanField(()-> bean.setSurname(requestString("Cognome: ")));
                    case 3 -> validateBeanField(()-> bean.setDateOfBirth(requestDate("Data di nascita (gg-mm-aaaa): ")));
                    case 4 -> {
                        validateBeanField(()-> bean.setPassword(requestString("Nuova password: ")));
                        validateBeanField(()-> bean.setConfirmPassword(requestString("Conferma nuova password: ")));
                        controller.updateUserPassword(bean);
                        print("Password modificata con successo");
                    }
                    case 5 -> {
                        if (bean instanceof PlayerAccountBean p) {
                            validateBeanField(()-> p.setPosition(PlayerPosition.fromString(requestString("Nuova posizione preferita (portiere, difensore, centrocampista, attaccante): "))));
                        } else {
                            FieldManagerAccountBean f = (FieldManagerAccountBean) bean;
                            validateBeanField(()-> f.setPhoneNumber(requestString("Nuovo numero di telefono: ")));
                        }
                    }
                    case 0 -> {
                        controller.updateUserData(bean);
                        clearScreen();
                        print("Modifiche applicate con successo.");
                        PageManager.pop();
                    }
                    default -> throw new IllegalStateException("Valore imprevisto: " + choice);
                }
            } catch (SessionExpiredException e) {
                showExceptionMessage(e);
                PageManager.push(() -> new LoginCli().login());
                return;
            } catch (IllegalArgumentException e) {
                showExceptionMessage(e);
            }
        }
    }

    private void showOption(Role role) {
        List<String> menu = new ArrayList<>();
        menu.add("Modifica nome");
        menu.add("Modifica cognome");
        menu.add("Modifica data di nascita");
        menu.add("Modifica password");
        showMenu(menu);

        if (role == Role.PLAYER) {
            print("5) Modifica posizione preferita");
        } else {
            print("5) Modifica numero di telefono");
        }

        print("Digita 0 per applicare le modifiche");
    }
}
