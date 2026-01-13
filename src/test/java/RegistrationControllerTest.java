import it.calcettohub.bean.RegisterPlayerBean;
import it.calcettohub.controller.RegistrationController;
import it.calcettohub.dao.PlayerDao;
import it.calcettohub.dao.abstractfactory.DaoFactory;
import it.calcettohub.exceptions.EmailAlreadyExistsException;
import it.calcettohub.model.Player;
import it.calcettohub.model.PlayerPosition;
import it.calcettohub.utils.PasswordUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

class RegistrationControllerTest {
    private static final String TEST_EMAIL = "test.player@calcettohub.local";
    private static final String PLAIN_PASSWORD = "Password@123";

    private RegistrationController controller;
    private PlayerDao playerDao;

    @BeforeEach
    void setUp() {
        controller = new RegistrationController();
        playerDao = DaoFactory.getInstance().getPlayerDao();

        // Cleanup: cancella l'utente di test se già esiste nel sistema (pulizia preventiva)
        playerDao.delete(TEST_EMAIL);
    }

    @AfterEach
    void tearDown() {
        // Cleanup finale
        playerDao.delete(TEST_EMAIL);
    }
    /**
     * Viene testato un esempio di registrazione corretta di un player
     */
    @Test
    void testPlayerRegistrationSuccessfully() {
        RegisterPlayerBean bean = new RegisterPlayerBean();
        bean.setName("Luigi");
        bean.setSurname("Verdi");
        bean.setDateOfBirth(LocalDate.parse("10-01-2000", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        bean.setEmail(TEST_EMAIL);
        bean.setPassword(PLAIN_PASSWORD);
        bean.setConfirmPassword(PLAIN_PASSWORD);
        bean.setPreferredPosition(PlayerPosition.DEFENDER);

        Assertions.assertDoesNotThrow(() ->controller.registerPlayer(bean));

        // Verifico che ora esista nel sistema
        Optional<Player> opt = playerDao.findByEmail(TEST_EMAIL);
        Assertions.assertTrue(opt.isPresent());

        // Verifico che la password sia hashata è valida
        Player saved = opt.get();
        Assertions.assertNotEquals(PLAIN_PASSWORD, saved.getPassword());
        Assertions.assertTrue(PasswordUtils.checkPassword(PLAIN_PASSWORD, saved.getPassword()));
    }

    /**
     * Viene testato un esempio di registrazione di un player errato a causa
     * dell'inserimento di un'email già esistente nel sistema
     */
    @Test
    void testPlayerRegistrationEmailAlreadyExists() throws EmailAlreadyExistsException {
        RegisterPlayerBean first = new RegisterPlayerBean();
        first.setName("Luigi");
        first.setSurname("Verdi");
        first.setDateOfBirth(LocalDate.of(2000, 1, 10));
        first.setEmail(TEST_EMAIL);
        first.setPassword(PLAIN_PASSWORD);
        first.setConfirmPassword(PLAIN_PASSWORD);
        first.setPreferredPosition(PlayerPosition.DEFENDER);

        controller.registerPlayer(first);

        // Seconda registrazione con stessa email -> eccezione
        RegisterPlayerBean second = new RegisterPlayerBean();
        second.setName("Altro");
        second.setSurname("Utente");
        second.setDateOfBirth(LocalDate.of(2001, 2, 3));
        second.setEmail(TEST_EMAIL);
        second.setPassword("AnotherPassword@123");
        second.setConfirmPassword("AnotherPassword@123");
        second.setPreferredPosition(PlayerPosition.STRIKER);

        Assertions.assertThrows(EmailAlreadyExistsException.class, () -> controller.registerPlayer(second));
    }
}
