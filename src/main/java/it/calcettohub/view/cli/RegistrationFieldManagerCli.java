package it.calcettohub.view.cli;

import it.calcettohub.bean.RegisterFieldManagerBean;
import it.calcettohub.controller.RegistrationController;
import it.calcettohub.exceptions.EmailAlreadyExistsException;
import it.calcettohub.util.PageManager;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class RegistrationFieldManagerCli extends CliContext {
    private final RegistrationController registration = new RegistrationController();

    public void fieldManagerRegistration() {
        // ESC → torna alla schermata precedente
        CliContext.setEscHandler(() -> {
            print("Torno alla pagina precedente...");
            PageManager.pop();
        });

        RegisterFieldManagerBean bean = new RegisterFieldManagerBean();
        while (true) {
            while (true) {
                try {
                    String name = requestString("Nome: ");
                    bean.setName(name);
                    break;
                } catch (IllegalArgumentException e) {
                    showExceptionMessage(e);
                }
            }

            while (true) {
                try {
                    String surname = requestString("Cognome: ");
                    bean.setSurname(surname);
                    break;
                } catch (IllegalArgumentException e) {
                    showExceptionMessage(e);
                }
            }

            while (true) {
                try {
                    LocalDate dateOfBirth = requestDate("Data di nascita (gg-mm-aaaa): ");
                    bean.setDateOfBirth(dateOfBirth);
                    break;
                } catch (IllegalArgumentException | DateTimeParseException e) {
                    showExceptionMessage(e);
                }
            }

            while (true) {
                try {
                    String email = requestString("Email: ");
                    bean.setEmail(email);
                    break;
                } catch (IllegalArgumentException e) {
                    showExceptionMessage(e);
                }
            }

            while (true) {
                try {
                    String password = requestString("Password: ");
                    bean.setPassword(password);
                    break;
                } catch (IllegalArgumentException e) {
                    showExceptionMessage(e);
                }
            }

            while (true) {
                try {
                    String confirmPassword = requestString("Conferma password: ");
                    bean.setConfirmPassword(confirmPassword);
                    break;
                } catch (IllegalArgumentException e) {
                    showExceptionMessage(e);
                }
            }

            while (true) {
                try {
                    String vatNumber = requestString("Partita IVA: ");
                    bean.setVatNumber(vatNumber);
                    break;
                } catch (IllegalArgumentException e) {
                    showExceptionMessage(e);
                }
            }

            while (true) {
                try {
                    String phoneNumber = requestString("Numero di telefono: ");
                    bean.setPhoneNumber(phoneNumber);
                    break;
                } catch (IllegalArgumentException e) {
                    showExceptionMessage(e);
                }
            }

            try {
                registration.registerFieldManager(bean);
                print("Registrazione completata con successo.");
                break;
            } catch (EmailAlreadyExistsException e) {
                showExceptionMessage(e);
            }
        }
    }
}
