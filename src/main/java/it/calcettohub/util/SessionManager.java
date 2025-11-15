package it.calcettohub.util;

public class SessionManager {
    private static SessionManager instance;
    private Session currentSession;

    private SessionManager() {}

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public synchronized void createSession(String userEmail) {
        if (currentSession != null && !currentSession.isExpired()) {
            currentSession.resetExpiryTime();
            return;
        }
        currentSession = new Session(userEmail);
    }

    public synchronized Session getCurrentSession() {
        if (currentSession == null) {
            return null;
        }
        if (currentSession.isExpired()) {
            closeSession();
            return null;
        }

        return currentSession;
    }

    public synchronized void refreshSession() {
        if (currentSession != null && !currentSession.isExpired()) {
            currentSession.resetExpiryTime();
        }
    }

    public synchronized void closeSession() {
        currentSession = null;
    }
}
