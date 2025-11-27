package it.calcettohub.dao;

import it.calcettohub.model.FieldManager;

import java.time.LocalDate;

public class FieldManagerFileSystemDao extends UserFileSystemDao<FieldManager> implements FieldManagerDao {
    private static FieldManagerFileSystemDao instance;
    private static final String FILE_PATH = "src/main/resources/csv/fieldManager.csv";

    public static synchronized FieldManagerFileSystemDao getInstance() {
        if (instance == null) {
            instance = new FieldManagerFileSystemDao();
        }
        return instance;
    }

    @Override
    protected String getFilePath() {
        return FILE_PATH;
    }

    @Override
    protected FieldManager fromCsvArray(String[] fields) {
        return new FieldManager(fields[0], fields[1], fields[2], fields[3], LocalDate.parse(fields[4]), fields[5], fields[6]);
    }
}
