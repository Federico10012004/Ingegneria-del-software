import it.calcettohub.dao.FieldManagerDemoDao;
import it.calcettohub.model.FieldManager;
import it.calcettohub.util.DemoRepository;
import it.calcettohub.util.PasswordUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class FieldManagerDemoDaoTest {

    @Test
    public void testFieldManagerDemoDaoFlow() {
        System.out.println("==== TEST FieldManagerDemoDao ====");

        DemoRepository repo = DemoRepository.getInstance();
        FieldManagerDemoDao dao = FieldManagerDemoDao.getInstance();

        // 1️⃣ Repository inizialmente vuoto
        System.out.println("FieldManager salvati PRIMA del test: " + repo.getFieldManagers().size());
        Assertions.assertEquals(0, repo.getFieldManagers().size(), "Il repository deve essere vuoto all'avvio");

        // 2️⃣ Creazione nuovo FieldManager
        FieldManager fm1 = new FieldManager(
                "luca.bianchi@centrocampo.it",
                "gestore123",
                "Luca",
                "Bianchi",
                LocalDate.of(1985, 3, 20),
                "3456789012",
                "3405567891"
        );

        dao.add(fm1);

        System.out.println("✅ Aggiunto gestore: " + fm1.getName() + " " + fm1.getSurname());
        System.out.println("Password hashata: " + fm1.getPassword());
        System.out.println("Numero gestori dopo add(): " + repo.getFieldManagers().size());
        Assertions.assertTrue(repo.getFieldManagers().containsKey("luca.bianchi@centrocampo.it"));

        // 3️⃣ Update (senza cambiare password)
        String oldHash = fm1.getPassword();
        fm1.setPhoneNumber("3400000000"); // Simula una modifica
        fm1.setName("Mario");
        fm1.setSurname("Rossi");
        dao.update(fm1);
        System.out.println("\n✏️ Dopo update (senza cambio password):");
        System.out.println("Telefono aggiornato: " + fm1.getPhoneNumber());
        System.out.println("Nome aggiornato: " + fm1.getName());
        System.out.println("Cognome aggiornato: " + fm1.getSurname());
        System.out.println("Password invariata: " + fm1.getPassword());
        Assertions.assertEquals(oldHash, fm1.getPassword(), "La password non deve cambiare nell'update");

        // 4️⃣ Cambio password
        dao.updatePassword("luca.bianchi@centrocampo.it", "nuovaPasswordGestore!");
        FieldManager updated = dao.findByEmail("luca.bianchi@centrocampo.it").get();
        System.out.println("\n🔑 Dopo cambio password:");
        System.out.println("Nuova password hashata: " + updated.getPassword());
        Assertions.assertTrue(
                PasswordUtils.checkPassword("nuovaPasswordGestore!", updated.getPassword()),
                "La nuova password deve corrispondere all'hash"
        );

        // 5️⃣ Eliminazione
        dao.delete("luca.bianchi@centrocampo.it");
        System.out.println("\n🗑️ Dopo delete, gestori rimasti: " + repo.getFieldManagers().size());
        Assertions.assertEquals(0, repo.getFieldManagers().size(), "Dopo delete il repository deve essere vuoto");

        System.out.println("==== FINE TEST ====\n");
    }
}
