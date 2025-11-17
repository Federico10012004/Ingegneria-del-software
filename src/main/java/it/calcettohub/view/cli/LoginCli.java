package it.calcettohub.view.cli;

import it.calcettohub.bean.LoginBean;
import it.calcettohub.controller.LoginController;
import it.calcettohub.exceptions.EmailNotFoundException;
import it.calcettohub.exceptions.EscPressedException;
import it.calcettohub.exceptions.InvalidPasswordException;
import it.calcettohub.model.User;
import it.calcettohub.util.AppContext;
import it.calcettohub.util.PageManager;
import it.calcettohub.util.Session;
import it.calcettohub.util.SessionManager;

public class LoginCli extends CliContext {
    private final LoginController controller = new LoginController();

    public void login() {

        // ESC → torna alla schermata precedente
        CliContext.setEscHandler(() -> {
            print("Torno alla pagina precedente...");
            PageManager.pop();
        });

        Exception lastError = null;

        while (true) {

            if (lastError != null) {
                showErrorMessage(lastError);
                System.out.println();
            }
            printTitle("Login " + AppContext.getSelectedRole());
            printEscInfo();
            System.out.println();

            try {
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

                print("Login effettuato con successo!");
                switch (user.getRole()) {
                    case PLAYER -> new HomePagePlayerCli().start();
                    case FIELDMANAGER -> new HomePageFieldManagerCli().start();
                    default -> {
                        return;
                    }
                }
                break;
            } catch (EscPressedException e) {
                // Return to the previous page
                return;
            } catch (EmailNotFoundException | InvalidPasswordException | IllegalArgumentException e) {
                lastError = e;
            }
        }
    }
}
