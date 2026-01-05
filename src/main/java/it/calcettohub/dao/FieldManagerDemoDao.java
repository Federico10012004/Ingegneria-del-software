package it.calcettohub.dao;

import it.calcettohub.model.FieldManager;
import it.calcettohub.utils.DemoRepository;

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
        fieldManagers.put(email,manager);
    }

    @Override
    public void update(FieldManager manager) {
        String email = manager.getEmail();
        FieldManager existingManager = fieldManagers.get(email);

        if (existingManager == null) {
            return;
        }

        manager.setPassword(existingManager.getPassword());

        fieldManagers.put(email, manager);
    }

    @Override
    public void updatePassword(String email, String newPassword) {
        FieldManager manager = fieldManagers.get(email);

        if (manager == null) {
            return;
        }

        manager.setPassword(newPassword);
    }

    @Override
    public void delete(String email) {
        fieldManagers.remove(email);
    }

    @Override
    public Optional<FieldManager> findByEmail(String email) {
        return Optional.ofNullable(fieldManagers.get(email));
    }
}
