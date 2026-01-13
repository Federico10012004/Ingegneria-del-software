package controllerTest;

import it.calcettohub.bean.LoginBean;
import it.calcettohub.controller.LoginController;
import it.calcettohub.exceptions.EmailNotFoundException;
import it.calcettohub.exceptions.InvalidPasswordException;
import it.calcettohub.model.Player;
import it.calcettohub.model.Role;
import it.calcettohub.utils.SessionManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LoginControllerTest {

    @BeforeEach
    void resetSession() {
        SessionManager.getInstance().closeSession();
    }

    /**
     * Viene testato un esempio di login corretto di un player
     */
    @Test
    void testLoginPlayerSuccessfully() {
        LoginController controller = new LoginController();

        LoginBean bean = new LoginBean();
        bean.setEmail("federico.guglielmini@students.eu");
        bean.setPassword("PasswordCorretta@10");
        bean.setRole(Role.PLAYER);

        Assertions.assertDoesNotThrow(() -> controller.login(bean));

        // Verifica che la sessione venga creata
        Assertions.assertNotNull(SessionManager.getInstance().getCurrentSession());

        // Verifica che l'utente in sessione sia corretto
        Assertions.assertEquals(
                "federico.guglielmini@students.eu",
                SessionManager.getInstance().getLoggedUser().getEmail()
        );

        // Verifica che il tipo dell'utente in sessione sia corretto
        Assertions.assertInstanceOf(Player.class, SessionManager.getInstance().getLoggedUser());
    }

    /**
     * Viene testato un esempio di login fallito di un field manager a causa di un'email non trovata
     */
    @Test
    void testLoginIncorrectEmailNotFound() {
        LoginController controller = new LoginController();

        LoginBean bean = new LoginBean();
        bean.setEmail("emailNonPresente@email.com");
        bean.setPassword("Password@109");
        bean.setRole(Role.FIELDMANAGER);

        Assertions.assertThrows(EmailNotFoundException.class, () -> controller.login(bean));

        // Verifica che nessuna sessione venga creata
        Assertions.assertNull(SessionManager.getInstance().getCurrentSession());
    }

    /**
     * Viene testato un esempio di login fallito di un player a causa di una password errata
     */
    @Test
    void testLoginIncorrectInvalidPassword() {
        LoginController controller = new LoginController();

        LoginBean bean = new LoginBean();
        bean.setEmail("federico.guglielmini@students.eu");
        bean.setPassword("PasswordErrata@10");
        bean.setRole(Role.PLAYER);

        Assertions.assertThrows(InvalidPasswordException.class, () -> controller.login(bean));

        // Verifica che nessuna sessione venga creata
        Assertions.assertNull(SessionManager.getInstance().getCurrentSession());
    }
}