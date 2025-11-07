package it.calcettohub.dao;

import it.calcettohub.model.Player;
import it.calcettohub.model.PlayerPosition;
import it.calcettohub.util.DatabaseConnection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PlayerDatabaseDao implements PlayerDao {
    private static PlayerDatabaseDao instance;
    private static final String ADD_PLAYER = "{call add_player(?, ?, ?, ?, ?, ?, ?)}";
    private static final String DELETE_PLAYER = "{call delete_player(?)}";
    private static final String ALL_PLAYER = "{call find_all_player()}";

    public static synchronized PlayerDatabaseDao getInstance() {
        if (instance == null) {
            instance = new PlayerDatabaseDao();
        }
        return instance;
    }

    @Override
    public void add(Player player) {
        String email = player.getEmail();
        String password = player.getPassword();
        String name = player.getName();
        String surname = player.getSurname();
        LocalDate dateOfBirth = player.getDateOfBirth();
        PlayerPosition position = player.getPreferredPosition();
        LocalDate registrationDate = player.getRegistrationDate();

        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (CallableStatement stmt = conn.prepareCall(ADD_PLAYER)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, name);
            stmt.setString(4, surname);
            stmt.setDate(5, java.sql.Date.valueOf(dateOfBirth));
            stmt.setString(6, position.name());
            stmt.setDate(7, java.sql.Date.valueOf(registrationDate));

            stmt.execute();
        } catch (SQLException _) {

        }
    }

    @Override
    public void delete(String email) {
        Connection conn = DatabaseConnection.getInstance().getConnection();

        try (CallableStatement stmt = conn.prepareCall(DELETE_PLAYER)) {
            stmt.setString(1, email);

            stmt.execute();
        } catch (SQLException _) {

        }
    }

    @Override
    public List<Player> findAll() {
        Connection conn = DatabaseConnection.getInstance().getConnection();

        try (CallableStatement stmt = conn.prepareCall()){

        }
    }

    @Override
    public Player findByEmail(String email) {

    }
}