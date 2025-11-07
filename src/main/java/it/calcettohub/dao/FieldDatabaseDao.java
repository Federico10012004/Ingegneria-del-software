package it.calcettohub.dao;

public class FieldDatabaseDao implements FieldDao {
    private static FieldDatabaseDao instance;

    public static synchronized FieldDatabaseDao getInstance() {
        if (instance== null) {
            instance = new FieldDatabaseDao();
        }
        return instance;
    }
}
