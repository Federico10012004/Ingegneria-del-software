package it.calcettohub.view.cli;

import it.calcettohub.bean.RegisterPlayerBean;
import it.calcettohub.controller.RegistrationController;
import it.calcettohub.exceptions.EmailAlreadyExistsException;
import it.calcettohub.exceptions.EscPressedException;
import it.calcettohub.model.PlayerPosition;
import it.calcettohub.utils.AppContext;
import it.calcettohub.utils.PageManager;

public class PlayerRegistrationCli extends BaseRegistrationCli<RegisterPlayerBean> {
    private final RegistrationController registration = new RegistrationController();

    public void playerRegistration() {

        disableSessionCheck();

        // ESC → torna alla schermata precedente
        setEscHandler(() -> {
            clearScreen();
            PageManager.pop();
        });

        RegisterPlayerBean bean = new RegisterPlayerBean();
        while (true) {
            printTitle("Registrazione " + AppContext.getSelectedRole());
            printEscInfo();

            try {
                validateAllFields(bean);

                registration.registerPlayer(bean);
                print("Registrazione effettuata con successo.");
                PageManager.pop();
                return;
            } catch (EmailAlreadyExistsException e) {
                showExceptionMessage(e);
            }  catch (EscPressedException _) {
                return;
            }
        }
    }

    @Override
    protected void fillSpecificFields(RegisterPlayerBean bean) {
        validateBeanField(()-> bean.setPreferredPosition(PlayerPosition.fromString(requestString("Posizione preferita (portiere, difensore, centrocampista, attaccante): "))));
    }
}
