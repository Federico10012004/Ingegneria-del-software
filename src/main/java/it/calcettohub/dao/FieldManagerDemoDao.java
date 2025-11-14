package it.calcettohub.dao;

import it.calcettohub.model.FieldManager;
import it.calcettohub.util.DemoRepository;
import it.calcettohub.util.PasswordUtils;

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

        String hashedPassword = PasswordUtils.hashPassword(manager.getPassword());
        manager.setPassword(hashedPassword);

        fieldManagers.put(email,manager);
    }

    @Override
    public void update(FieldManager manager) {
        String email = manager.getEmail();
        FieldManager existingManager = fieldManagers.get(email);

        if (existingManager == null) {
            return;
        }

        // conserva la password hashata
        manager.setPassword(existingManager.getPassword());

        fieldManagers.put(email, manager);
    }

    @Override
    public void updatePassword(String email, String newPassword) {
        FieldManager manager = fieldManagers.get(email);

        if (manager == null) {
            return;
        }

        String newHashedPassword = PasswordUtils.hashPassword(newPassword);
        manager.setPassword(newHashedPassword);
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
