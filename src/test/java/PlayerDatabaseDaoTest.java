import it.calcettohub.dao.PlayerDatabaseDao;
import it.calcettohub.model.Player;
import it.calcettohub.model.PlayerPosition;
import org.junit.jupiter.api.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PlayerDatabaseDaoTest {

    private static PlayerDatabaseDao dao;
    private static Player testPlayer;

    @BeforeAll
    public static void setup() {
        dao = PlayerDatabaseDao.getInstance();

        // Crea un giocatore di prova
        testPlayer = new Player(
                "testplayer@example.com",
                "password123",
                "Mario",
                "Rossi",
                LocalDate.of(1998, 5, 15),
                PlayerPosition.DEFENDER
        );
    }

    @Test
    @Order(1)
    public void testAddPlayer() {
        assertDoesNotThrow(() -> dao.add(testPlayer),
                "Errore durante l'inserimento del player nel DB");
    }

    @Test
    @Order(2)
    public void testFindPlayer() {
        Player found = dao.findByEmail("testplayer@example.com");

        assertNotNull(found, "Il player non è stato trovato nel DB");
        assertEquals(testPlayer.getEmail(), found.getEmail());
        assertEquals(testPlayer.getName(), found.getName());
        assertEquals(testPlayer.getSurname(), found.getSurname());
        assertEquals(testPlayer.getPreferredPosition(), found.getPreferredPosition());
    }

    @Test
    @Order(3)
    public void testDeletePlayer() {
        assertDoesNotThrow(() -> dao.delete("testplayer@example.com"),
                "Errore durante l'eliminazione del player");

        Player foundAfterDelete = dao.findByEmail("testplayer@example.com");
        assertNull(foundAfterDelete, "Il player non dovrebbe più esistere nel DB");
    }
}
