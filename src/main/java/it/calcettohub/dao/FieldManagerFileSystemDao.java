package it.calcettohub.dao;

import it.calcettohub.model.FieldManager;

public class FieldManagerFileSystemDao implements FieldManagerDao {
    private static FieldManagerFileSystemDao instance;

    public static synchronized FieldManagerFileSystemDao getInstance() {
        if (instance == null) {
            instance = new FieldManagerFileSystemDao();
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
