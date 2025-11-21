package it.calcettohub.view.cli;

import it.calcettohub.bean.LoginBean;
import it.calcettohub.controller.LoginController;
import it.calcettohub.exceptions.EmailNotFoundException;
import it.calcettohub.exceptions.EscPressedException;
import it.calcettohub.exceptions.InvalidPasswordException;
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

        LoginBean bean = new LoginBean();
        bean.setRole(AppContext.getSelectedRole());

        while (true) {

            printTitle("Login " + AppContext.getSelectedRole());
            printEscInfo();
            System.out.println();

            try {
                validateBeanField(()-> bean.setEmail(requestString("Email: ")));
                validateBeanField(()-> bean.setPassword(requestString("Password: ")));

                controller.login(bean);

                Session session = SessionManager.getInstance().getCurrentSession();

                if (session == null) {
                    System.err.println("Errore: impossibile creare la sessione.");
                    return;
                }

                print("Login effettuato con successo!");
                switch (AppContext.getSelectedRole()) {
                    case PLAYER -> System.out.println("Benvenuto player");
                    case FIELDMANAGER -> System.out.println("Benvenuto Field Manger");
                    default -> throw new RuntimeException();
                }
                break;
            } catch (EscPressedException e) {
                // Return to the previous page
                return;
            } catch (EmailNotFoundException | InvalidPasswordException | IllegalArgumentException e) {
                showExceptionMessage(e);
            } catch (RuntimeException e) {
                System.err.println("Errore inattesso nello switch della pagina.");
                System.exit(1);
            }
        }
    }
}
