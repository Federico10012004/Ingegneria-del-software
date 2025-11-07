package it.calcettohub.dao;

import it.calcettohub.model.FieldManager;

import java.util.List;

public interface FieldManagerDao {
    void save(FieldManager manager);
    void delete(String email);
    List<FieldManager> findAll();
    FieldManager findByEmail(String email);
}
