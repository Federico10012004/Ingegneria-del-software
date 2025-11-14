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

    public void createSession(String userEmail) {
        currentSession = new Session(userEmail);
    }

    public Session getCurrentSession() {
        if (currentSession == null) {
            return null;
        }
        if (currentSession.isExpired()) {
            closeSession();
            return null;
        }

        return currentSession;
    }

    public void refreshSession() {
        if (currentSession != null && !currentSession.isExpired()) {
            currentSession.resetExpiryTime();
        }
    }

    public void closeSession() {
        currentSession = null;
    }
}
