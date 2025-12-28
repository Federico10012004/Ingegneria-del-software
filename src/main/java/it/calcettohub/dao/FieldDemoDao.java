package it.calcettohub.dao;

import it.calcettohub.model.Field;
import it.calcettohub.utils.DemoRepository;

import java.util.List;
import java.util.Map;

public class FieldDemoDao implements FieldDao {
    private static FieldDemoDao instance;
    private final Map<String, Field> fields;

    public FieldDemoDao() {
        this.fields = DemoRepository.getInstance().getFields();
    }

    public static synchronized FieldDemoDao getInstance() {
        if (instance == null) {
            instance = new FieldDemoDao();
        }
        return instance;
    }

    @Override
    public void add(Field field) {
        String id = field.getId();
        fields.put(id, field);
    }

    @Override
    public void delete(String id) {
        fields.remove(id);
    }

    @Override
    public List<Field> findFieldsByManager(String manager) {
        return fields.values().stream()
                .filter(f -> manager.equals(f.getManager()))
                .toList();
    }

    @Override
    public List<Field> searchFields(String fieldAddress, String fieldCity) {
        return null;
    }

    @Override
    public Field findById(String id) {
        return null;
    }
}
