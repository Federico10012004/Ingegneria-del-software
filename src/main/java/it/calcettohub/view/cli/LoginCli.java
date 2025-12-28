package it.calcettohub.view.cli;

import it.calcettohub.bean.LoginBean;
import it.calcettohub.controller.LoginController;
import it.calcettohub.exceptions.EmailNotFoundException;
import it.calcettohub.exceptions.EscPressedException;
import it.calcettohub.exceptions.InvalidPasswordException;
import it.calcettohub.exceptions.UnexpectedRoleException;
import it.calcettohub.utils.AppContext;
import it.calcettohub.utils.PageManager;
import it.calcettohub.utils.Session;
import it.calcettohub.utils.SessionManager;

public class LoginCli extends CliContext {
    private final LoginController controller = new LoginController();

    public void login() {

        disableSessionCheck();

        setEscHandler(() -> {
            clearScreen();
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
                    showErrorMessage("Errore inatteso: impossibile creare la sessione.");
                    System.exit(1);
                }

                clearScreen();
                print("Login effettuato con successo!");
                switch (AppContext.getSelectedRole()) {
                    case PLAYER -> PageManager.push(()->new HomePagePlayerCli().start());
                    case FIELDMANAGER -> PageManager.push(()->new HomePageFieldManagerCli().start());
                    default -> throw new UnexpectedRoleException("Ruolo inatteso: " + AppContext.getSelectedRole());
                }

                return;
            } catch (EmailNotFoundException | InvalidPasswordException | IllegalArgumentException e) {
                showExceptionMessage(e);
            } catch (UnexpectedRoleException _) {
                System.exit(1);
            }  catch (EscPressedException _) {
                return;
            }
        }
    }
}
