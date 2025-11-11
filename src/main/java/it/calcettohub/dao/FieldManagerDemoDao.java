package it.calcettohub.dao;

import it.calcettohub.model.FieldManager;
import it.calcettohub.util.DemoRepository;

import java.util.Map;
import java.util.Optional;

public class FieldManagerDemoDao implements FieldManagerDao {
    private static FieldManagerDemoDao instance;
    private final Map<String, FieldManager> fieldManagers;

    public FieldManagerDemoDao() {
        this.fieldManagers = DemoRepository.getInstance().getFieldManagers();
    }

    public static synchronized FieldManagerDemoDao getInstance() {
        if (instance == null) {
            instance = new FieldManagerDemoDao();
        }
        return instance;
    }

    @Override
    public void add(FieldManager manager) {
        String email = manager.getEmail();
        if (fieldManagers.containsKey(email)) {
            return;
        }

        fieldManagers.put(email,manager);
    }

    @Override
    public void delete(String email) {
        if (!fieldManagers.containsKey(email)) {
            return;
        }

        fieldManagers.remove(email);
    }

    @Override
    public Optional<FieldManager> findByEmail(String email) {
        return Optional.ofNullable(fieldManagers.get(email));
    }
}
