package it.calcettohub.dao;

import it.calcettohub.model.FieldManager;

public interface FieldManagerDao {
    void add(FieldManager manager);
    void delete(String email);
    FieldManager findByEmail(String email);
}
