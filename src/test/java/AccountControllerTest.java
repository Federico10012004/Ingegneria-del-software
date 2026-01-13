import it.calcettohub.bean.FieldManagerAccountBean;
import it.calcettohub.bean.PlayerAccountBean;
import it.calcettohub.controller.AccountController;
import it.calcettohub.dao.FieldManagerDao;
import it.calcettohub.dao.PlayerDao;
import it.calcettohub.dao.abstractfactory.DaoFactory;
import it.calcettohub.model.FieldManager;
import it.calcettohub.model.Player;
import it.calcettohub.model.PlayerPosition;
import it.calcettohub.utils.PasswordUtils;
import it.calcettohub.utils.SessionManager;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

class AccountControllerTest {

    private static final String PLAYER_EMAIL = "test.player@calcettohub.local";
    private static final String FM_EMAIL = "test.fm@calcettohub.local";

    private static final String OLD_PASS = "OldPass@123";
    private static final String NEW_PASS = "NewPass@123";

    private AccountController controller;
    private PlayerDao playerDao;
    private FieldManagerDao fieldManagerDao;

    @BeforeEach
    void setUp() {
        controller = new AccountController();
        playerDao = DaoFactory.getInstance().getPlayerDao();
        fieldManagerDao = DaoFactory.getInstance().getFieldManagerDao();

        // Cleanup dati test + sessione
        SessionManager.getInstance().closeSession();
        playerDao.delete(PLAYER_EMAIL);
        fieldManagerDao.delete(FM_EMAIL);
    }

    @AfterEach
    void tearDown() {
        SessionManager.getInstance().closeSession();
        playerDao.delete(PLAYER_EMAIL);
        fieldManagerDao.delete(FM_EMAIL);
    }

    /**
     * Viene testato metodo di update di password e alcuni dati del player
     */
    @Test
    void testPlayerChangePasswordAndApplyDataChanges() {
        // Crea player nel sistema + sessione
        String oldHashed = PasswordUtils.hashPassword(OLD_PASS);
        Player p = new Player(
                PLAYER_EMAIL, oldHashed,
                "Mario", "Rossi",
                LocalDate.of(2000, 1, 10),
                PlayerPosition.DEFENDER
        );
        playerDao.add(p);
        SessionManager.getInstance().createSession(p);

        PlayerAccountBean bean = new PlayerAccountBean();

        bean.setPassword(NEW_PASS);
        bean.setConfirmPassword(NEW_PASS);
        controller.updateUserPassword(bean);

        bean.setName("Luigi");
        bean.setPosition(PlayerPosition.STRIKER);
        controller.updateUserData(bean);

        // Prendo il player dal sistema per controllare gli aggiornamenti
        Player updated = playerDao.findByEmail(PLAYER_EMAIL).orElseThrow();

        // Password aggiornata e hashata
        Assertions.assertNotEquals(oldHashed, updated.getPassword());
        Assertions.assertNotEquals(NEW_PASS, updated.getPassword());
        Assertions.assertTrue(PasswordUtils.checkPassword(NEW_PASS, updated.getPassword()));

        // Dati aggiornati
        Assertions.assertEquals("Luigi", updated.getName());
        Assertions.assertEquals("Rossi", updated.getSurname());
        Assertions.assertEquals(PlayerPosition.STRIKER, updated.getPreferredPosition());
    }

    /**
     * Viene testato metodo di update di un solo dato del player e gli altri devono rimanere invariati
     */
    @Test
    void testUpdateUserDataPlayerPartialUpdateOnlyPositionChanges() {
        String oldHashed = PasswordUtils.hashPassword(OLD_PASS);
        Player p = new Player(
                PLAYER_EMAIL, oldHashed,
                "Mario", "Rossi",
                LocalDate.of(2000, 1, 10),
                PlayerPosition.DEFENDER
        );
        playerDao.add(p);
        SessionManager.getInstance().createSession(p);

        PlayerAccountBean bean = new PlayerAccountBean();
        bean.setPosition(PlayerPosition.MIDFIELDER); // Setto solo questo

        controller.updateUserData(bean);

        Player updated = playerDao.findByEmail(PLAYER_EMAIL).orElseThrow();
        Assertions.assertEquals("Mario", updated.getName());
        Assertions.assertEquals("Rossi", updated.getSurname());
        Assertions.assertEquals(PlayerPosition.MIDFIELDER, updated.getPreferredPosition());
    }

    /**
     * Viene testato metodo di update di password e alcuni dati del field manager
     */
    @Test
    void testUpdateUserDataFieldManager() {
        // Crea field manager nel sistema + sessione
        String oldHashed = PasswordUtils.hashPassword(OLD_PASS);
        FieldManager fm = new FieldManager(
                FM_EMAIL, oldHashed,
                "Franco", "Bianchi",
                LocalDate.of(1985, 9, 8),
                "07694831520",
                "333000111"
        );
        fieldManagerDao.add(fm);
        SessionManager.getInstance().createSession(fm);

        FieldManagerAccountBean bean = new FieldManagerAccountBean();

        bean.setPassword(NEW_PASS);
        bean.setConfirmPassword(NEW_PASS);
        controller.updateUserPassword(bean);

        bean.setName("Giorgio");
        bean.setPhoneNumber("3889745522");
        controller.updateUserData(bean);

        FieldManager updated = fieldManagerDao.findByEmail(FM_EMAIL).orElseThrow();

        Assertions.assertNotEquals(oldHashed, updated.getPassword());
        Assertions.assertNotEquals(NEW_PASS, updated.getPassword());
        Assertions.assertTrue(PasswordUtils.checkPassword(NEW_PASS, updated.getPassword()));

        Assertions.assertEquals("Giorgio", updated.getName());
        Assertions.assertEquals("Bianchi", updated.getSurname());
        Assertions.assertEquals("3889745522", updated.getPhoneNumber());
    }

    @Test
    void testUpdateUserDataFieldManagerPartialUpdateOnlyPhoneChanges() {
        String oldHashed = PasswordUtils.hashPassword(OLD_PASS);
        FieldManager fm = new FieldManager(
                FM_EMAIL, oldHashed,
                "Franco", "Bianchi",
                LocalDate.of(1985, 9, 8),
                "07694831520",
                "333000111"
        );
        fieldManagerDao.add(fm);
        SessionManager.getInstance().createSession(fm);

        FieldManagerAccountBean bean = new FieldManagerAccountBean();
        bean.setPhoneNumber("389 75 46 899"); // Setto solo questo

        controller.updateUserData(bean);

        FieldManager updated = fieldManagerDao.findByEmail(FM_EMAIL).orElseThrow();
        Assertions.assertEquals("Franco", updated.getName());
        Assertions.assertEquals("Bianchi", updated.getSurname());
        Assertions.assertEquals("389 75 46 899", updated.getPhoneNumber());
    }
}