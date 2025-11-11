package it.calcettohub.dao;

import it.calcettohub.model.Player;

import java.util.Optional;

public class PlayerFileSystemDao implements PlayerDao {
    private static PlayerFileSystemDao instance;

    public static synchronized PlayerFileSystemDao getInstance() {
        if (instance == null) {
            instance = new PlayerFileSystemDao();
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
    public Optional<Player> findByEmail(String email) {
        return null;
    }
}
