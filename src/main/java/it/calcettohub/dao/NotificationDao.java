package it.calcettohub.dao;

import it.calcettohub.model.valueobject.Notification;

import java.util.List;

public interface NotificationDao {
    void add(Notification notification);
    List<Notification> findUnreadByUserEmail(String userEmail);
    void markAllRead(String userEmail);
}
