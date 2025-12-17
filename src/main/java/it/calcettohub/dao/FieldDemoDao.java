package it.calcettohub.dao;

import it.calcettohub.model.Field;

import java.util.List;

public class FieldDemoDao implements FieldDao {
    private static FieldDemoDao instance;

    public static synchronized FieldDemoDao getInstance() {
        if (instance == null) {
            instance = new FieldDemoDao();
        }
        return instance;
    }

    @Override
    public void add(Field field) {}

    @Override
    public void delete(String id) {}

    @Override
    public List<Field> findFields(String manager) {
        return null;
    }
}
