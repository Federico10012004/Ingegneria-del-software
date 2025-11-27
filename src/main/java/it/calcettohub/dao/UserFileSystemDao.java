package it.calcettohub.dao;

import it.calcettohub.exceptions.PersistenceException;
import it.calcettohub.model.User;
import it.calcettohub.util.PasswordUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class UserFileSystemDao<T extends User> {
    protected static final String SEP = ";";

    protected abstract String getFilePath();
    protected abstract T fromCsvArray(String[] fields);

    protected void writeAll(List<String> lines) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFilePath()))) {
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new PersistenceException("Errore durante la scrittura del file csv", e);
        }
    }

    public void add(T user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFilePath(), true))) {
            writer.write(String.join(SEP, user.getAllFields()));
            writer.newLine();
        } catch (IOException e) {
            throw new PersistenceException("Errore durante il salvataggio del giocatore", e);
        }
    }

    public void delete(String email) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath()))) {
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

    public void update(T user) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath()))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] arr = line.split(SEP);

                String csvEmail = arr[0];
                if (csvEmail.equals(user.getEmail())) {
                    lines.add(String.join(SEP, user.getAllFields()));
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            throw new PersistenceException("Errore durante la lettura del file csv", e);
        }

        writeAll(lines);
    }

    public void updatePassword(String email, String newPassword) {
        Optional<T> optionalUser = findByEmail(email);

        if (optionalUser.isPresent()) {
            T user = optionalUser.get();
            String hashedPassword = PasswordUtils.hashPassword(newPassword);

            user.setPassword(hashedPassword);
            update(user);
        }
    }

    public Optional<T> findByEmail(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath()))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] array = line.split(SEP);

                String csvEmail = array[0];
                if (csvEmail.equals(email)) {
                    return Optional.of(fromCsvArray(array));
                }
            }
            return Optional.empty();
        } catch (IOException e) {
            throw new PersistenceException("Errore nel caricamento nel player", e);
        }
    }
}
