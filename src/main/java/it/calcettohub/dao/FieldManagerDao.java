package it.calcettohub.dao;

import it.calcettohub.model.FieldManager;

import java.util.Optional;

public interface FieldManagerDao {
    void add(FieldManager manager);
    void delete(String email);
    Optional<FieldManager> findByEmail(String email);
}
