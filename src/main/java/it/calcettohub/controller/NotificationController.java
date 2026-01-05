package it.calcettohub.controller;

import it.calcettohub.dao.NotificationDao;
import it.calcettohub.dao.abstractfactory.DaoFactory;
import it.calcettohub.model.Role;
import it.calcettohub.model.User;
import it.calcettohub.model.valueobject.Notification;
import it.calcettohub.utils.SessionManager;

import java.util.List;

public class NotificationController {
    private final NotificationDao notificationDao = DaoFactory.getInstance().getNotificationDao();

    public List<Notification> getNotifications() {
        User user = SessionManager.getInstance().getLoggedUser();
        String userEmail = user.getEmail();
        Role role = user.getRole();

        return notificationDao.findUnreadNotification(userEmail, role);
    }

    public void markAsAlreadyRead() {
        User user = SessionManager.getInstance().getLoggedUser();
        String userEmail = user.getEmail();
        Role role = user.getRole();

        notificationDao.markAllRead(userEmail, role);
    }
}
