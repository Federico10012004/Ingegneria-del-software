package it.calcettohub.dao;

import it.calcettohub.model.Player;

public class PlayerDemoDao implements PlayerDao {
    private static PlayerDemoDao instance;

    public static synchronized PlayerDemoDao getInstance() {
        if (instance == null) {
            instance = new PlayerDemoDao();
        }
        return instance;
    }

    @Override
    public void add(Player manager) {

    }

    @Override
    public void delete(String email) {

    }

    @Override
    public Player findByEmail(String email) {
        return null;
    }
}
