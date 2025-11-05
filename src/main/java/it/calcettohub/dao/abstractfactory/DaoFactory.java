package it.calcettohub.dao.abstractfactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class DaoFactory {
    private static final String CONFIG_PROPERTIES = "config.properties";

    private static class LazyHolder {
        private static final DaoFactory instance = createInstance();
    }

    private static DaoFactory createInstance() {
        Properties properties = new Properties();
        try (InputStream input = DaoFactory.class.getClassLoader().getResourceAsStream(CONFIG_PROPERTIES)) {
            if (input != null) {
                properties.load(input);
            } else {
                System.err.println("File config.properties not found, defaulting to demo mode.");
            }
        } catch (IOException _) {
            System.err.println("Error loading config.properties.");
        }

        String mode = properties.getProperty("mode").trim().toLowerCase();
        return switch (mode) {
            case "database" -> new DatabaseFactory();
            case "filesystem" -> new FileSystemFactory();
            default -> new DemoFactory();
        };
    }

    public static DaoFactory getInstance() {
        return LazyHolder.instance;
    }
}
