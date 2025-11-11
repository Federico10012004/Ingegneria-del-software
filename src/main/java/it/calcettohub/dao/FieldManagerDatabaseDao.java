package it.calcettohub.dao;

import it.calcettohub.model.FieldManager;
import it.calcettohub.util.DatabaseConnection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

public class FieldManagerDatabaseDao implements FieldManagerDao {
    private static FieldManagerDatabaseDao instance;
    private static final String ADD_MANAGER = "{call add_manager(?, ?, ?, ?, ?, ?, ?)}";
    private static final String DELETE_MANAGER = "{call delete_manager(?)}";
    private static final String VIEW_MANAGER = "{call find_manager(?)}";

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
            stmt.setDate(5, java.sql.Date.valueOf(dateOfBirth));
            stmt.setString(6, vatNumber);
            stmt.setString(7, phoneNumber);

            stmt.execute();
        } catch (SQLException _) {

        }
    }

    @Override
    public void delete(String email) {
        Connection conn = DatabaseConnection.getInstance().getConnection();

        try (CallableStatement stmt = conn.prepareCall(DELETE_MANAGER)) {
            stmt.setString(1, email);

            stmt.execute();
        } catch (SQLException _) {

        }
    }

    @Override
    public Optional<FieldManager> findByEmail(String manager_email) {
        Connection conn = DatabaseConnection.getInstance().getConnection();

        try (CallableStatement stmt = conn.prepareCall(VIEW_MANAGER)) {
            stmt.setString(1, manager_email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String email = rs.getString("email");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                LocalDate dateOfBirth = LocalDate.parse(rs.getString("dateOfBirth"));
                String vatNumber = rs.getString("vatNumber");
                String phoneNumber = rs.getString("phoneNumber");

                FieldManager manager = new FieldManager(email, name, surname, dateOfBirth, vatNumber, phoneNumber);
                return Optional.of(manager);
            }
        } catch (SQLException _) {

        }
        return Optional.empty();
    }
}
