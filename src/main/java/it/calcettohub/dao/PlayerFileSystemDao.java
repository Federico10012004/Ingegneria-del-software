package it.calcettohub.dao;

import it.calcettohub.exceptions.PersistenceException;
import it.calcettohub.model.Player;
import it.calcettohub.model.PlayerPosition;
import it.calcettohub.util.PasswordUtils;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlayerFileSystemDao implements PlayerDao {
    private static PlayerFileSystemDao instance;
    private static final String FILE_PATH = "src/main/resources/csv/player.csv";
    private static final String SEP = ";";

    public static synchronized PlayerFileSystemDao getInstance() {
        if (instance == null) {
            instance = new PlayerFileSystemDao();
        }
        return instance;
    }

    @Override
    public void add(Player player) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(String.join(SEP, player.getAllFields()));
            writer.newLine();
        } catch (IOException e) {
            throw new PersistenceException("Errore durante il salvataggio del giocatore", e);
        }
    }

    @Override
    public void update(Player player) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] arr = line.split(SEP);

                String csvEmail = arr[0];
                if (csvEmail.equals(player.getEmail())) {
                    lines.add(String.join(SEP, player.getAllFields()));
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            throw new PersistenceException("Errore durante la lettura del file csv", e);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new PersistenceException("Errore durante la scrittura del file csv", e);
        }
    }

    @Override
    public void updatePassword(String email, String newPassword) {
        Optional<Player> optionalPlayer = findByEmail(email);

        if (optionalPlayer.isPresent()){
            Player player = optionalPlayer.get();
            String hashedPassword = PasswordUtils.hashPassword(newPassword);

            player.setPassword(hashedPassword);
            update(player);
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

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new PersistenceException("Errore durante la scrittura del file csv", e);
        }
    }

    @Override
    public Optional<Player> findByEmail(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] array = line.split(SEP);

                String csvEmail = array[0];
                if (csvEmail.equals(email)) {
                    LocalDate dateOfBirth = LocalDate.parse(array[4]);
                    PlayerPosition position = PlayerPosition.fromString(array[5]);
                    Player player = new Player(array[0], array[1], array[2], array[3], dateOfBirth, position);
                    return Optional.of(player);
                }
            }
            return Optional.empty();
        } catch (IOException e) {
            throw new PersistenceException("Errore nel caricamento nel player", e);
        }
    }
}
