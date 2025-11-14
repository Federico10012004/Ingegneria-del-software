package it.calcettohub.util;

import java.time.Duration;
import java.time.LocalDateTime;

public class Session {
    private static final Duration TIMEOUT = Duration.ofMinutes(30);

    private final String userEmail;
    private LocalDateTime expiryTime;

    public Session(String userEmail) {
        this.userEmail = userEmail;
        resetExpiryTime();
    }

    public String getUserEmail() {
        return userEmail;
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
