package it.calcettohub.view.cli;

import it.calcettohub.bean.RegisterFieldManagerBean;
import it.calcettohub.controller.RegistrationController;
import it.calcettohub.exceptions.EmailAlreadyExistsException;
import it.calcettohub.util.AppContext;
import it.calcettohub.util.PageManager;

public class FieldManagerRegistrationCli extends BaseRegistrationCli<RegisterFieldManagerBean> {
    private final RegistrationController registration = new RegistrationController();

    public void fieldManagerRegistration() {

        disableSessionCheck();

        // ESC → torna alla schermata precedente
        setEscHandler(() -> {
            clearScreen();
            PageManager.pop();
        });

        RegisterFieldManagerBean bean = new RegisterFieldManagerBean();
        while (true) {
            printTitle("Registrazione " + AppContext.getSelectedRole());
            printEscInfo();

            try {
                validateAllFields(bean);

                registration.registerFieldManager(bean);
                print("Registrazione completata con successo.");
                PageManager.pop();
                return;
            } catch (EmailAlreadyExistsException e) {
                showExceptionMessage(e);
            }
        }
    }

    @Override
    protected void fillSpecificFields(RegisterFieldManagerBean bean) {
        validateBeanField(() -> bean.setVatNumber(requestString("Partita iva: ")));
        validateBeanField(() -> bean.setPhoneNumber(requestString("Numero di telefono: ")));
    }
}
