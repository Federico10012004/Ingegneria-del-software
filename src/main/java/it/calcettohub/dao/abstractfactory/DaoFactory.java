package it.calcettohub.dao.abstractfactory;

import it.calcettohub.dao.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class DaoFactory {

    public abstract FieldDao getFieldDao();
    public abstract PlayerDao getPlayerDao();
    public abstract FieldManagerDao getFieldManagerDao();

    private static DaoFactory instance;
    private static final String CONFIG_PROPERTIES = "config.properties";

    public static synchronized DaoFactory getInstance() {
        if (instance == null) {
            Properties properties = new Properties();
            try (InputStream input = DaoFactory.class.getClassLoader().getResourceAsStream(CONFIG_PROPERTIES)) {
                if (input != null) {
                    properties.load(input);
                } else {
                    System.err.println("File di configurazione non trovato, inizializzazione della modalità demo.");
                }
            } catch (IOException _) {
                System.err.println("Errore nel caricamento del file di configurazione.");
            }

            String mode = properties.getProperty("mode", "demo").trim().toLowerCase();
            switch (mode) {
                case "database":
                    instance = new DatabaseFactory();
                    break;
                case "filesystem":
                    instance = new FileSystemFactory();
                    break;
                case "demo":
                    instance = new DemoFactory();
                    break;
                default:
                    instance = new DemoFactory();
                    break;
            }
        }
        return instance;
    }
}
