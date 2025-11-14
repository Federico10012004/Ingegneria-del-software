import it.calcettohub.dao.PlayerDatabaseDao;
import it.calcettohub.model.Player;
import it.calcettohub.model.PlayerPosition;
import it.calcettohub.util.DatabaseConnection;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PlayerDatabaseDaoTest {

    private static final PlayerDatabaseDao dao = PlayerDatabaseDao.getInstance();

    @BeforeEach
    void cleanBefore() throws Exception {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM player")) {
            ps.executeUpdate();
        }
    }

    // -----------------------------
    // 1) TEST ADD + FIND
    // -----------------------------
    @Test
    @Order(1)
    void testAddAndFind() {
        Player p = new Player(
                "test@example.com",
                "Password123_@10a",
                "Mario",
                "Rossi",
                LocalDate.of(2000, 1, 1),
                PlayerPosition.STRIKER
        );

        dao.add(p);

        Optional<Player> found = dao.findByEmail("test@example.com");

        assertTrue(found.isPresent());
        assertEquals("Mario", found.get().getName());
        assertEquals(PlayerPosition.STRIKER, found.get().getPreferredPosition());
    }

    // -----------------------------
    // 2) TEST UPDATE
    // -----------------------------
    @Test
    @Order(2)
    void testUpdate() {
        // Inseriamo giocatore
        Player p = new Player(
                "test2@example.com",
                "password123",
                "Luca",
                "Bianchi",
                LocalDate.of(1998, 5, 20),
                PlayerPosition.MIDFIELDER
        );

        dao.add(p);

        // Modifica
        Player updated = new Player(
                "test2@example.com",
                "password123",
                "Luciano", // update
                "Bianchi",
                LocalDate.of(1998, 5, 20),
                PlayerPosition.DEFENDER
        );

        dao.update(updated);

        Optional<Player> found = dao.findByEmail("test2@example.com");

        assertTrue(found.isPresent());
        assertEquals("Luciano", found.get().getName());
        assertEquals(PlayerPosition.DEFENDER, found.get().getPreferredPosition());
    }

    // -----------------------------
    // 3) TEST UPDATE PASSWORD
    // -----------------------------
    @Test
    @Order(3)
    void testUpdatePassword() {
        Player p = new Player(
                "test3@example.com",
                "oldPassword",
                "Marco",
                "Verdi",
                LocalDate.of(1999, 3, 10),
                PlayerPosition.GOALKEEPER
        );

        dao.add(p);

        dao.updatePassword("test3@example.com", "newPassword123");

        Optional<Player> found = dao.findByEmail("test3@example.com");

        assertTrue(found.isPresent());
        assertNotEquals("newPassword123", found.get().getPassword(),
                "La password nel DB deve essere HASHATA");

        assertTrue(found.get().getPassword().startsWith("$2"),
                "La password aggiornata deve essere bcrypt");
    }
/*
    // -----------------------------
    // 4) TEST DELETE
    // -----------------------------
    @Test
    @Order(4)
    void testDelete() {
        Player p = new Player(
                "delete@example.com",
                "pass",
                "Andrea",
                "Ricci",
                LocalDate.of(2001, 7, 7),
                PlayerPosition.DEFENDER
        );

        dao.add(p);

        dao.delete("delete@example.com");

        Optional<Player> found = dao.findByEmail("delete@example.com");

        assertTrue(found.isEmpty());
    }

 */
}
