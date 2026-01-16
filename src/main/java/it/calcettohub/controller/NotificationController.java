package it.calcettohub.controller;

import it.calcettohub.bean.NotificationBean;
import it.calcettohub.dao.NotificationDao;
import it.calcettohub.dao.abstractfactory.DaoFactory;
import it.calcettohub.model.Role;
import it.calcettohub.model.User;
import it.calcettohub.model.valueobject.Notification;
import it.calcettohub.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class NotificationController {
    private final NotificationDao notificationDao = DaoFactory.getInstance().getNotificationDao();

    public List<NotificationBean> getNotifications() {
        User user = SessionManager.getInstance().getLoggedUser();
        String userEmail = user.getEmail();
        Role role = user.getRole();

        List<NotificationBean> results = new ArrayList<>();
        List<Notification> notifications = notificationDao.findUnreadNotification(userEmail, role);
        for (Notification n : notifications) {
            NotificationBean bean = new NotificationBean(n.id(), n.userEmail(), n.userRole(), n.message(), n.isRead());
            results.add(bean);
        }

        return results;
    }

    public void markAsAlreadyRead() {
        User user = SessionManager.getInstance().getLoggedUser();
        String userEmail = user.getEmail();
        Role role = user.getRole();

        notificationDao.markAllRead(userEmail, role);
    }
}
