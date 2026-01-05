package it.calcettohub.dao;

import it.calcettohub.model.Role;
import it.calcettohub.model.valueobject.Notification;

import java.util.List;

public interface NotificationDao {
    void add(Notification notification);
    List<Notification> findUnreadNotification(String userEmail, Role userRole);
    void markAllRead(String userEmail, Role userRole);
}
