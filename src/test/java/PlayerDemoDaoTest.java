import it.calcettohub.dao.PlayerDemoDao;
import it.calcettohub.model.Player;
import it.calcettohub.model.PlayerPosition;
import it.calcettohub.util.DemoRepository;
import it.calcettohub.util.PasswordUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class PlayerDemoDaoTest {

    @Test
    public void testPlayerDemoDaoFlow() {
        System.out.println("==== TEST PlayerDemoDao ====");

        DemoRepository repo = DemoRepository.getInstance();
        PlayerDemoDao dao = PlayerDemoDao.getInstance();

        // 1️⃣ Repository inizialmente vuoto
        System.out.println("Giocatori salvati PRIMA del test: " + repo.getPlayers().size());
        Assertions.assertEquals(0, repo.getPlayers().size(), "Il repository deve essere vuoto all'avvio");

        // 2️⃣ Creazione nuovo player
        Player p1 = new Player(
                "mario.rossi@example.com",
                "Password123_@10a",
                "Mario",
                "Rossi",
                LocalDate.of(1992, 10, 5),
                PlayerPosition.STRIKER);

        dao.add(p1);

        System.out.println("✅ Aggiunto: " + p1.getName() + " " + p1.getSurname());
        System.out.println("Password hashata: " + p1.getPassword());
        System.out.println("Numero utenti dopo add(): " + repo.getPlayers().size());
        Assertions.assertTrue(repo.getPlayers().containsKey("mario.rossi@example.com"));

        // 3️⃣ Update senza cambiare password
        String oldHash = p1.getPassword();
        p1.setName("Mario G. Rossi");
        dao.update(p1);
        System.out.println("\n✏️ Dopo update: " + p1.getName());
        System.out.println("Password invariata: " + p1.getPassword());
        Assertions.assertEquals(oldHash, p1.getPassword(), "La password non deve cambiare nell'update");

        // 4️⃣ Cambio password
        dao.updatePassword("mario.rossi@example.com", "nuovaPass321");
        Player updated = dao.findByEmail("mario.rossi@example.com").get();
        System.out.println("\n🔑 Dopo cambio password:");
        System.out.println("Nuova password hashata: " + updated.getPassword());
        Assertions.assertTrue(PasswordUtils.checkPassword("nuovaPass321", updated.getPassword()));

        System.out.println("==== FINE TEST ====\n");
    }
}
