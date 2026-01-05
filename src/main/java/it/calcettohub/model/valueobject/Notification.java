package it.calcettohub.model.valueobject;

import it.calcettohub.model.Role;

import java.util.UUID;

public record Notification(
        String id,
        String userEmail,
        Role userRole,
        String message,
        boolean isRead
) {
    public static Notification unread(String userEmail, Role userRole, String message) {
        return new Notification(UUID.randomUUID().toString(), userEmail, userRole, message, false);
    }

    public Notification markRead() {
        return isRead ? this : new Notification(id, userEmail, userRole, message, true);
    }
}