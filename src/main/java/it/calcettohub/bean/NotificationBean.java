package it.calcettohub.bean;

import it.calcettohub.model.Role;

public class NotificationBean {
    private String id;
    private String userEmail;
    private Role role;
    private String message;
    private boolean isRead;

    public NotificationBean(String id, String userEmail, Role role, String message, boolean isRead) {
        this.id = id;
        this.userEmail = userEmail;
        this.role = role;
        this.message = message;
        this.isRead = isRead;
    }

    public String getId() {
        return id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public Role getRole() {
        return role;
    }

    public String getMessage() {
        return message;
    }

    public boolean isRead() {
        return isRead;
    }
}
