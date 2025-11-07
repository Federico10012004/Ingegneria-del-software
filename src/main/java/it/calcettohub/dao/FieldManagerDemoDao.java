package it.calcettohub.dao;

public class FieldManagerDemoDao implements FieldManagerDao {
    private static FieldManagerDemoDao instance;

    public static synchronized FieldManagerDemoDao getInstance() {
        if (instance == null) {
            instance = new FieldManagerDemoDao();
        }
        return instance;
    }
}
