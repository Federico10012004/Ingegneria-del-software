package it.calcettohub.dao;

import it.calcettohub.model.Field;

import java.util.List;

public interface FieldDao {
    void add(Field field);
    void delete(String id);
    List<Field> findFieldsByManager(String manager);
    List<Field> searchFields(String fieldAddress, String fieldCity);
    Field findById(String id);
}
