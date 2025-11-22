package it.calcettohub.view.cli;

import it.calcettohub.bean.RegisterFieldManagerBean;
import it.calcettohub.controller.RegistrationController;
import it.calcettohub.exceptions.EmailAlreadyExistsException;
import it.calcettohub.util.PageManager;

public class FieldManagerRegistrationCli extends CliContext {
    private final RegistrationController registration = new RegistrationController();

    public void fieldManagerRegistration() {
        // ESC → torna alla schermata precedente
        CliContext.setEscHandler(() -> {
            print("Torno alla pagina precedente...");
            PageManager.pop();
        });

        RegisterFieldManagerBean bean = new RegisterFieldManagerBean();
        while (true) {
            try {
                validateBeanField(() -> bean.setName(requestString("Nome: ")));
                validateBeanField(() -> bean.setSurname(requestString("Cognome: ")));
                validateBeanField(() -> bean.setDateOfBirth(requestDate("Data di nascita (gg-mm-aaaa): ")));
                validateBeanField(() -> bean.setEmail(requestString("Email: ")));
                validateBeanField(() -> bean.setPassword(requestString("Password: ")));
                validateBeanField(() -> bean.setConfirmPassword(requestString("Conferma password: ")));
                validateBeanField(() -> bean.setVatNumber(requestString("Partita iva: ")));
                validateBeanField(() -> bean.setPhoneNumber(requestString("Numero di telefono: ")));

                registration.registerFieldManager(bean);
                print("Registrazione completata con successo.");
                PageManager.pop();
                break;
            } catch (EmailAlreadyExistsException e) {
                showExceptionMessage(e);
            }
        }
    }
}
