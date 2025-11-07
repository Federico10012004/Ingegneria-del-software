package it.calcettohub.dao;

public class FieldManagerFileSystemDao implements FieldManagerDao {
    private static FieldManagerFileSystemDao instance;

    public static synchronized FieldManagerFileSystemDao getInstance() {
        if (instance == null) {
            instance = new FieldManagerFileSystemDao();
        }
        return instance;
    }
}
