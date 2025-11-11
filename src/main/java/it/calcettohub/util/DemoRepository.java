package it.calcettohub.util;

import it.calcettohub.model.FieldManager;
import it.calcettohub.model.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Simula un database in memoria condiviso tra i DemoDao.
 * Tutto viene perso alla chiusura dell'app.
 */

public class DemoRepository {

    private static DemoRepository instance;
    private final Map<String, Player> players;
    private final Map<String, FieldManager> fieldManagers;

    private DemoRepository() {
        this.players = new HashMap<>();
        this.fieldManagers = new HashMap<>();
    }

    public static synchronized DemoRepository getInstance() {
        if (instance == null) {
            instance = new DemoRepository();
        }
        return instance;
    }

    public Map<String, Player> getPlayers() {
        return players;
    }

    public Map<String, FieldManager> getFieldManagers() {
        return fieldManagers;
    }
}
