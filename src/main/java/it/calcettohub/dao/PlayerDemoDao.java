package it.calcettohub.dao;

public class PlayerDemoDao implements PlayerDao {
    private static PlayerDemoDao instance;

    public static synchronized PlayerDemoDao getInstance() {
        if (instance == null) {
            instance = new PlayerDemoDao();
        }
        return instance;
    }
}
