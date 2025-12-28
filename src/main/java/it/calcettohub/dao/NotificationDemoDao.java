package it.calcettohub.dao;

import it.calcettohub.model.valueobject.Notification;

import java.util.List;

public class NotificationDemoDao implements NotificationDao {
    private static NotificationDemoDao instance;

    public static synchronized NotificationDemoDao getInstance() {
        if (instance== null) {
            instance = new NotificationDemoDao();
        }
        return instance;
    }

    public void add(Notification notification) {

    }

    public List<Notification> findUnreadByUserEmail(String userEmail) {
        return null;
    }

    public void markAllRead(String userEmail) {

    }
}
