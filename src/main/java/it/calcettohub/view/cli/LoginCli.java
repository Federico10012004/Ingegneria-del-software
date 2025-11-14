package it.calcettohub.view.cli;

import it.calcettohub.bean.LoginBean;
import it.calcettohub.controller.LoginController;
import it.calcettohub.exceptions.EmailNotFoundException;
import it.calcettohub.exceptions.InvalidPasswordException;
import it.calcettohub.model.User;
import it.calcettohub.util.AppContext;
import it.calcettohub.util.Session;
import it.calcettohub.util.SessionManager;

import java.io.IOException;

public class LoginCli extends CliContext {
    private final LoginController controller = new LoginController();

    public void login() {

        try {
            System.out.println("=== Login " + AppContext.getSelectedRole() + " ===");
            String email = requestString("Email: ");
            String password = requestString("Password: ");

            LoginBean bean = new LoginBean();
            bean.setEmail(email);
            bean.setPassword(password);

            User user = controller.login(bean);

            Session session = SessionManager.getInstance().getCurrentSession();

            if (session == null) {
                System.err.println("Errore: impossibile creare la sessione.");
                return;
            }

            System.out.println("Login effettuato con successo!");
            switch (user.getRole()) {
                case PLAYER -> new HomePagePlayerCli().start();
                case FIELDMANAGER -> new HomePageFieldManagerCli().start();
            }

        } catch (EmailNotFoundException | InvalidPasswordException | IOException e) {
            showErrorMessage(e);
        }
    }
}
