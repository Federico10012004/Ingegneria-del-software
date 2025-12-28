package it.calcettohub.dao;

import it.calcettohub.exceptions.PersistenceException;
import it.calcettohub.model.Player;
import it.calcettohub.model.PlayerPosition;
import it.calcettohub.utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

public class PlayerDatabaseDao implements PlayerDao {
    private static PlayerDatabaseDao instance;
    private static final String ADD_PLAYER = "{call add_player(?, ?, ?, ?, ?, ?)}";
    private static final String DELETE_PLAYER = "{call delete_player(?)}";
    private static final String FIND_PLAYER = "{call find_player(?)}";
    private static final String UPDATE_PLAYER = "{call update_player(?, ?, ?, ?, ?)}";
    private static final String UPDATE_PASSWORD = "{call update_password_player(?, ?)}";

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

        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (CallableStatement stmt = conn.prepareCall(ADD_PLAYER)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, name);
            stmt.setString(4, surname);
            stmt.setDate(5, Date.valueOf(dateOfBirth));
            stmt.setString(6, position.name());

            stmt.execute();
        } catch (SQLException e) {
            throw new PersistenceException("Errore nell'aggiunta del player", e);
        }
    }

    @Override
    public void update(Player player) {
        String email = player.getEmail();
        String name = player.getName();
        String surname = player.getSurname();
        PlayerPosition position = player.getPreferredPosition();

        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (CallableStatement stmt = conn.prepareCall(UPDATE_PLAYER)) {
            stmt.setString(1, email);
            stmt.setString(2, name);
            stmt.setString(3, surname);
            stmt.setString(4, position.name());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException("Errore nell'update del player", e);
        }
    }

    @Override
    public void updatePassword(String email, String newPassword) {
        Connection conn = DatabaseConnection.getInstance().getConnection();

        try (CallableStatement stmt = conn.prepareCall(UPDATE_PASSWORD)) {
            stmt.setString(1, email);
            stmt.setString(2, newPassword);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException("Errore nell'aggiornamento della password", e);
        }
    }

    @Override
    public void delete(String email) {
        Connection conn = DatabaseConnection.getInstance().getConnection();

        try (CallableStatement stmt = conn.prepareCall(DELETE_PLAYER)) {
            stmt.setString(1, email);

            stmt.execute();
        } catch (SQLException e) {
            throw new PersistenceException("Errore nell'eliminazione del player", e);
        }
    }

    @Override
    public Optional<Player> findByEmail(String playerEmail) {
        Connection conn = DatabaseConnection.getInstance().getConnection();

        try (CallableStatement stmt = conn.prepareCall(FIND_PLAYER)) {
            stmt.setString(1, playerEmail);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String email = rs.getString("email");
                String password = rs.getString("password");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                LocalDate dateOfBirth = LocalDate.parse(rs.getString("dateOfBirth"));
                PlayerPosition position = PlayerPosition.valueOf(rs.getString("preferredPosition"));

                Player player = new Player(email, password, name, surname, dateOfBirth, position);
                return Optional.of(player);
            }
        } catch (SQLException e) {
            throw new PersistenceException("Errore nella ricerca del player", e);
        }
        return Optional.empty();
    }
}