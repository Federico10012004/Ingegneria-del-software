package it.calcettohub.view.cli;

import it.calcettohub.bean.RegisterPlayerBean;
import it.calcettohub.controller.RegistrationController;
import it.calcettohub.exceptions.EmailAlreadyExistsException;
import it.calcettohub.model.PlayerPosition;
import it.calcettohub.util.PageManager;

public class PlayerRegistrationCli extends CliContext {
    private final RegistrationController registration = new RegistrationController();

    public void playerRegistration() {
        // ESC → torna alla schermata precedente
        CliContext.setEscHandler(() -> {
            print("Torno alla pagina precedente...");
            PageManager.pop();
        });

        RegisterPlayerBean bean = new RegisterPlayerBean();
        while (true) {
            try {
                validateBeanField(()-> bean.setName(requestString("Nome: ")));
                validateBeanField(()-> bean.setSurname(requestString("Cognome: ")));
                validateBeanField(()-> bean.setDateOfBirth(requestDate("Data di nascita (gg-mm-aaaa): ")));
                validateBeanField(()-> bean.setEmail(requestString("Email: ")));
                validateBeanField(()-> bean.setPassword(requestString("Password: ")));
                validateBeanField(()-> bean.setConfirmPassword(requestString("Conferma password: ")));
                validateBeanField(()-> bean.setPreferredPosition(PlayerPosition.fromString(requestString("Posizione preferita (portiere, difensore, centrocampista, attaccante): "))));

                registration.registerPlayer(bean);
                print("Registrazione effettuata con successo.");
                PageManager.pop();
                break;
            } catch (EmailAlreadyExistsException e) {
                showExceptionMessage(e);
            }
        }
    }
}
