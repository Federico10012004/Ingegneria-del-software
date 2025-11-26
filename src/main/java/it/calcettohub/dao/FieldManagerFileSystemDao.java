package it.calcettohub.dao;

import it.calcettohub.exceptions.PersistenceException;
import it.calcettohub.model.FieldManager;
import it.calcettohub.util.PasswordUtils;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FieldManagerFileSystemDao implements FieldManagerDao {
    private static FieldManagerFileSystemDao instance;
    private static final String FILE_PATH = "src/main/resources/csv/fieldManager.csv";
    private static final String SEP = ";";

    public static synchronized FieldManagerFileSystemDao getInstance() {
        if (instance == null) {
            instance = new FieldManagerFileSystemDao();
        }
        return instance;
    }

    @Override
    public void add(FieldManager manager) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(String.join(SEP, manager.getAllFields()));
            writer.newLine();
        } catch (IOException e) {
            throw new PersistenceException("Errore durante il salvataggio del giocatore", e);
        }
    }

    @Override
    public void update(FieldManager manager) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] arr = line.split(SEP);

                String csvEmail = arr[0];
                if (csvEmail.equals(manager.getEmail())) {
                    lines.add(String.join(SEP, manager.getAllFields()));
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            throw new PersistenceException("Errore durante la lettura del file csv", e);
        }

        writeAll(lines);
    }

    @Override
    public void updatePassword(String email, String newPassword) {
        Optional<FieldManager> optionalFieldManager = findByEmail(email);

        if (optionalFieldManager.isPresent()) {
            FieldManager manager = optionalFieldManager.get();
            String hashedPassword = PasswordUtils.hashPassword(newPassword);

            manager.setPassword(hashedPassword);
            update(manager);
        }
    }

    @Override
    public void delete(String email) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] arr = line.split(SEP);

                String csvEmail = arr[0];
                if (!csvEmail.equals(email)) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            throw new PersistenceException("Errore durante la lettura del file csv", e);
        }

        writeAll(lines);
    }

    @Override
    public Optional<FieldManager> findByEmail(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] array = line.split(SEP);

                String csvEmail = array[0];
                if (csvEmail.equals(email)) {
                    LocalDate dateOfBirth = LocalDate.parse(array[4]);
                    FieldManager manager = new FieldManager(array[0], array[1], array[2], array[3], dateOfBirth, array[5], array[6]);
                    return Optional.of(manager);
                }
            }
            return Optional.empty();
        } catch (IOException e) {
            throw new PersistenceException("Errore nel caricamento nel player", e);
        }
    }

    private void writeAll(List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new PersistenceException("Errore durante la scrittura del file csv", e);
        }
    }
}
