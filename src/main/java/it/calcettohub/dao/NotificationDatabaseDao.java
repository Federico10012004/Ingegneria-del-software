package it.calcettohub.dao;

import it.calcettohub.exceptions.PersistenceException;
import it.calcettohub.model.valueobject.Notification;
import it.calcettohub.utils.DatabaseConnection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationDatabaseDao implements NotificationDao {
    private static NotificationDatabaseDao instance;
    private static final String ADD_NOTIFICATION = "{call add_notification(?, ?, ?, ?)}";
    private static final String FIND_UNREAD_NOTIFICATION = "{call find_unread_notifications(?)}";
    private static final String MARK_ALL_READ = "{call mark_all_read(?)}";

    public static synchronized NotificationDatabaseDao getInstance() {
        if (instance== null) {
            instance = new NotificationDatabaseDao();
        }
        return instance;
    }

    public void add(Notification notification) {
        String id = notification.id();
        String userEmail = notification.userEmail();
        String message = notification.message();
        boolean is_read = notification.isRead();

        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (CallableStatement stmt = conn.prepareCall(ADD_NOTIFICATION)) {
            stmt.setString(1, id);
            stmt.setString(2, userEmail);
            stmt.setString(3, message);
            stmt.setBoolean(4, is_read);

            stmt.execute();
        } catch (SQLException e) {
            throw new PersistenceException("Errore nell'aggiunta della notifica", e);
        }
    }

    public List<Notification> findUnreadByUserEmail(String userEmail) {
        List<Notification> notifications = new ArrayList<>();

        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (CallableStatement stmt = conn.prepareCall(FIND_UNREAD_NOTIFICATION)) {
            stmt.setString(1, userEmail);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");
                String message = rs.getString("message");
                boolean is_read = rs.getBoolean("is_read");

                Notification not = new Notification(id, userEmail, message, is_read);
                notifications.add(not);
            }
        } catch (SQLException e) {
            throw new PersistenceException("Errore nella ricerca delle notifiche", e);
        }

        return notifications;
    }

    public void markAllRead(String userEmail) {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (CallableStatement stmt = conn.prepareCall(MARK_ALL_READ)) {
            stmt.setString(1, userEmail);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException("Errore nell'operazione di 'segnare come già letto' le notifiche", e);
        }
    }
}
