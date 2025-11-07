package it.calcettohub.dao;

public class PlayerFileSystemDao implements PlayerDao {
    private static PlayerFileSystemDao instance;

    public static synchronized PlayerFileSystemDao getInstance() {
        if (instance == null) {
            instance = new PlayerFileSystemDao();
        }
        return instance;
    }
}
