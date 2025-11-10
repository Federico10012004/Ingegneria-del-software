import it.calcettohub.dao.PlayerDatabaseDao;
import it.calcettohub.model.Player;
import it.calcettohub.model.PlayerPosition;
import it.calcettohub.util.DatabaseConnection;
import it.calcettohub.util.PasswordUtils;

import java.sql.*;
import java.time.LocalDate;

public class PasswordHashingTest {

    public static void main(String[] args) {
        try {
            String plainPassword = "password123";
            String hashedPassword = PasswordUtils.hashPassword(plainPassword);

            // Simula la creazione di un nuovo Player
            Player player = new Player();
            player.setEmail("test@example.com");
            player.setPassword(hashedPassword);
            player.setName("Mario");
            player.setSurname("Rossi");
            player.setDateOfBirth(LocalDate.of(1998, 5, 15));
            player.setPreferredPosition(PlayerPosition.DEFENDER);

            PlayerDatabaseDao dao = new PlayerDatabaseDao();
            dao.add(player);  // <-- salva il player con l’hash

            // Ora recuperiamo la password dal DB per verificare
            String storedHash = getPasswordFromDatabase("test@example.com");

            System.out.println("Password originale: " + plainPassword);
            System.out.println("Password nel DB: " + storedHash);
            System.out.println("Verifica: " + PasswordUtils.checkPassword(plainPassword, storedHash));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Metodo di supporto per leggere la password dal DB
    private static String getPasswordFromDatabase(String email) {
        String storedHash = null;

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             CallableStatement ps = conn.prepareCall("{ call find_player(?) }")) { // ✅ CORRETTO

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                storedHash = rs.getString("password");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return storedHash;
    }
}
