package it.calcettohub.dao;

import it.calcettohub.exceptions.PersistenceException;
import it.calcettohub.model.FieldManager;
import it.calcettohub.utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

public class FieldManagerDatabaseDao implements FieldManagerDao {
    private static FieldManagerDatabaseDao instance;
    private static final String ADD_MANAGER = "{call add_manager(?, ?, ?, ?, ?, ?, ?)}";
    private static final String DELETE_MANAGER = "{call delete_manager(?)}";
    private static final String VIEW_MANAGER = "{call find_manager(?)}";
    private static final String UPDATE_MANAGER = "{call update_manager(?, ?, ?, ?)}";
    private static final String UPDATE_PASSWORD_MANAGER = "{call update_password_manager(?, ?)}";

    public static synchronized FieldManagerDatabaseDao getInstance() {
        if (instance == null) {
            instance = new FieldManagerDatabaseDao();
        }
        return instance;
    }

    @Override
    public void add(FieldManager manager) {
        String email = manager.getEmail();
        String password = manager.getPassword();
        String name = manager.getName();
        String surname = manager.getSurname();
        LocalDate dateOfBirth = manager.getDateOfBirth();
        String vatNumber = manager.getVatNumber();
        String phoneNumber = manager.getPhoneNumber();

        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (CallableStatement stmt = conn.prepareCall(ADD_MANAGER)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, name);
            stmt.setString(4, surname);
            stmt.setDate(5, Date.valueOf(dateOfBirth));
            stmt.setString(6, vatNumber);
            stmt.setString(7, phoneNumber);

            stmt.execute();
        } catch (SQLException e) {
            throw new PersistenceException("Errore nell'aggiunta del field manager", e);
        }
    }

    @Override
    public void update(FieldManager manager) {
        String email = manager.getEmail();
        String name = manager.getName();
        String surname = manager.getSurname();
        String phone = manager.getPhoneNumber();

        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (CallableStatement stmt = conn.prepareCall(UPDATE_MANAGER)) {
            stmt.setString(1, email);
            stmt.setString(2, name);
            stmt.setString(3, surname);
            stmt.setString(4, phone);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException("Errore nell'update del field manager", e);
        }
    }

    @Override
    public void updatePassword(String email, String newPassword) {
        Connection conn = DatabaseConnection.getInstance().getConnection();

        try (CallableStatement stmt = conn.prepareCall(UPDATE_PASSWORD_MANAGER)) {
            stmt.setString(1, email);
            stmt.setString(2, newPassword);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException("Errore nell'update della password", e);
        }
    }

    @Override
    public void delete(String email) {
        Connection conn = DatabaseConnection.getInstance().getConnection();

        try (CallableStatement stmt = conn.prepareCall(DELETE_MANAGER)) {
            stmt.setString(1, email);

            stmt.execute();
        } catch (SQLException e) {
            throw new PersistenceException("Errore nell'eliminazione del field manager", e);
        }
    }

    @Override
    public Optional<FieldManager> findByEmail(String managerEmail) {

        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (CallableStatement stmt = conn.prepareCall(VIEW_MANAGER)) {
            stmt.setString(1, managerEmail);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String email = rs.getString("email");
                String password = rs.getString("password");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                LocalDate dateOfBirth = LocalDate.parse(rs.getString("dateOfBirth"));
                String vatNumber = rs.getString("vatNumber");
                String phoneNumber = rs.getString("phoneNumber");

                FieldManager manager = new FieldManager(email, password, name, surname, dateOfBirth, vatNumber, phoneNumber);
                return Optional.of(manager);
            }
        } catch (SQLException e) {
            throw new PersistenceException("Errore nella ricerca del field manager", e);
        }
        return Optional.empty();
    }
}
