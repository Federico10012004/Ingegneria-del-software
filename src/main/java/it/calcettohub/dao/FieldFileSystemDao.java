package it.calcettohub.dao;

public class FieldFileSystemDao implements FieldDao {
    private static FieldFileSystemDao instance;

    public static synchronized FieldFileSystemDao getInstance() {
        if (instance == null) {
            instance = new FieldFileSystemDao();
        }
        return instance;
    }
}
