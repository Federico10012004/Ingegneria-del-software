package it.calcettohub.dao;

import it.calcettohub.model.Role;
import it.calcettohub.model.valueobject.Notification;
import it.calcettohub.utils.DemoRepository;

import java.util.List;
import java.util.Map;

public class NotificationDemoDao implements NotificationDao {
    private static NotificationDemoDao instance;
    private final Map<String, Notification> notifications;

    public NotificationDemoDao() {
        this.notifications = DemoRepository.getInstance().getNotifications();
    }

    public static synchronized NotificationDemoDao getInstance() {
        if (instance== null) {
            instance = new NotificationDemoDao();
        }
        return instance;
    }

    @Override
    public void add(Notification notification) {
        String id = notification.id();
        notifications.put(id, notification);
    }

    @Override
    public List<Notification> findUnreadNotification(String userEmail, Role userRole) {
        return notifications.values().stream()
                .filter(n -> userEmail.equals(n.userEmail()) && userRole.equals(n.userRole()) && !n.isRead())
                .toList();
    }

    @Override
    public void markAllRead(String userEmail, Role userRole) {
        for (Map.Entry<String, Notification> entry : notifications.entrySet()) {
            Notification n = entry.getValue();

            if (userEmail.equals(n.userEmail()) && userRole.equals(n.userRole()) && !n.isRead()) {
                entry.setValue(n.markRead());
            }
        }
    }
}
