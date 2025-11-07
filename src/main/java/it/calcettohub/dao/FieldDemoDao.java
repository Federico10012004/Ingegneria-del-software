package it.calcettohub.dao;

public class FieldDemoDao implements FieldDao {
    private static FieldDemoDao instance;

    public static synchronized FieldDemoDao getInstance() {
        if (instance == null) {
            instance = new FieldDemoDao();
        }
        return instance;
    }
}
