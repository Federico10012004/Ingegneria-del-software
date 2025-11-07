package it.calcettohub.dao;

public class FieldManagerDatabaseDao implements FieldManagerDao {
    private static FieldManagerDatabaseDao instance;

    public static synchronized FieldManagerDatabaseDao getInstance() {
        if (instance == null) {
            instance = new FieldManagerDatabaseDao();
        }
        return instance;
    }
}
