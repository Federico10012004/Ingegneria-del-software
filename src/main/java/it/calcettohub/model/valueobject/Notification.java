package it.calcettohub.model.valueobject;

import java.util.UUID;

public record Notification(
        String id,
        String userEmail,
        String message,
        boolean isRead
) {
    public static Notification unread(String userEmail, String message) {
        return new Notification(UUID.randomUUID().toString(), userEmail, message, false);
    }
}