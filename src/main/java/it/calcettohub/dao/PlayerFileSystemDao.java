package it.calcettohub.dao;

import it.calcettohub.model.Player;
import it.calcettohub.model.PlayerPosition;

import java.time.LocalDate;

public class PlayerFileSystemDao extends UserFileSystemDao<Player> implements PlayerDao {
    private static PlayerFileSystemDao instance;
    private static final String FILE_PATH = "src/main/resources/csv/player.csv";

    public static synchronized PlayerFileSystemDao getInstance() {
        if (instance == null) {
            instance = new PlayerFileSystemDao();
        }
        return instance;
    }

    @Override
    protected String getFilePath() {
        return FILE_PATH;
    }

    @Override
    protected Player fromCsvArray(String[] fields) {
        return new Player(fields[0], fields[1], fields[2], fields[3], LocalDate.parse(fields[4]), PlayerPosition.fromString(fields[5]));
    }
}
