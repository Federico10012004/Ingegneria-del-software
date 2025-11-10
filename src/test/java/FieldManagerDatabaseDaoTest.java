import it.calcettohub.dao.FieldManagerDatabaseDao;
import it.calcettohub.model.FieldManager;
import org.junit.jupiter.api.*;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FieldManagerDatabaseDaoTest {

    private static FieldManagerDatabaseDao dao;
    private static FieldManager testManager;

    @BeforeAll
    public static void setup() {
        dao = FieldManagerDatabaseDao.getInstance();

        // Crea un gestore di prova
        testManager = new FieldManager(
                "testmanager@example.com",
                "password123",
                "Mario",
                "Rossi",
                LocalDate.of(1998, 5, 15),
                "12345678901",
                "3389675001");
    }

    @Test
    @Order(1)
    public void testAddManager() {
        assertDoesNotThrow(() -> dao.add(testManager),
                "Errore durante l'inserimento del manager nel DB");
    }

    @Test
    @Order(2)
    public void testFindManager() {
        FieldManager found = dao.findByEmail("testmanager@example.com");

        assertNotNull(found, "Il manager non è stato trovato nel DB");
        assertEquals(testManager.getEmail(), found.getEmail());
        assertEquals(testManager.getName(), found.getName());
        assertEquals(testManager.getSurname(), found.getSurname());
        assertEquals(testManager.getDateOfBirth(), found.getDateOfBirth());
        assertEquals(testManager.getVatNumber(), found.getVatNumber());
        assertEquals(testManager.getPhoneNumber(), found.getPhoneNumber());
    }

    @Test
    @Order(3)
    public void testDeleteManager() {
        assertDoesNotThrow(() -> dao.delete("testmanager@example.com"),
                "Errore durante l'eliminazione del manager");

        FieldManager foundAfterDelete = dao.findByEmail("testmanager@example.com");
        assertNull(foundAfterDelete, "Il manager non dovrebbe più esistere nel DB");
    }
}
