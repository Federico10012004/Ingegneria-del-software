package it.calcettohub.dao;

import it.calcettohub.model.FieldManager;

public class FieldManagerDemoDao implements FieldManagerDao {
    private static FieldManagerDemoDao instance;

    public static synchronized FieldManagerDemoDao getInstance() {
        if (instance == null) {
            instance = new FieldManagerDemoDao();
        }
        return instance;
    }

    @Override
    public void add(FieldManager manager) {

    }

    @Override
    public void delete(String email) {

    }

    @Override
    public FieldManager findByEmail(String email) {
        return null;
    }
}
