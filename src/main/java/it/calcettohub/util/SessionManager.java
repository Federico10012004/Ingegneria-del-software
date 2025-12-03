package it.calcettohub.util;

import it.calcettohub.exceptions.SessionExpiredException;
import it.calcettohub.model.User;

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

    public synchronized void createSession(User user) {
        currentSession = new Session(user);
    }

    public synchronized Session getCurrentSession() {
        if (currentSession == null) {
            return null;
        }
        if (currentSession.isExpired()) {
            closeSession();
            return null;
        }

        refreshSession();
        return currentSession;
    }

    public synchronized User getLoggedUser() {
        Session session = getCurrentSession();
        if (session == null) {
            throw new SessionExpiredException("Sessione scaduta.");
        }
        return session.getUser();
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
