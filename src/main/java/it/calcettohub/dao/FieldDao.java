package it.calcettohub.dao;

import it.calcettohub.model.Field;

import java.util.List;

public interface FieldDao {
    void add(Field field);
    void delete(String id);
    List<Field> findFields(String manager);
}
