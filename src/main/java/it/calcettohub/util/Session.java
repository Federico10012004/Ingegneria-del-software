package it.calcettohub.util;

import it.calcettohub.model.User;

import java.time.Duration;
import java.time.LocalDateTime;

public class Session {
    private static final Duration TIMEOUT = Duration.ofMinutes(1);

    private final User user;
    private LocalDateTime expiryTime;

    public Session(User user) {
        this.user = user;
        resetExpiryTime();
    }

    public User getUser() {
        return user;
    }

    public void resetExpiryTime() {
        this.expiryTime = LocalDateTime.now().plus(TIMEOUT);
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryTime);
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }
}
