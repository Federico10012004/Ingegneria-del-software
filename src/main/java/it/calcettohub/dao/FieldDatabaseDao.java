package it.calcettohub.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.calcettohub.exceptions.PersistenceException;
import it.calcettohub.model.Field;
import it.calcettohub.model.OpeningTime;
import it.calcettohub.model.SurfaceType;
import it.calcettohub.util.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.*;

public class FieldDatabaseDao implements FieldDao {
    private static FieldDatabaseDao instance;
    private static final String ADD_FIELD = "{call add_field(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    private static final String FIND_FIELDS = "{call find_fields(?)}";
    private static final String DELETE_FIELD = "{call delete_field(?)}";

    public static synchronized FieldDatabaseDao getInstance() {
        if (instance== null) {
            instance = new FieldDatabaseDao();
        }
        return instance;
    }

    @Override
    public void add(Field field) {
        String id = field.getId();
        String fieldName = field.getFieldName();
        String address = field.getAddress();
        String city = field.getCity();
        SurfaceType surface = field.getSurfaceType();
        boolean indoor = field.isIndoor();
        BigDecimal hourlyPrice = field.getHourlyPrice();
        String manager = field.getManager();

        String openingHoursJson = createJson(field.getOpeningHours());

        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (CallableStatement stmt = conn.prepareCall(ADD_FIELD)) {
            stmt.setString(1, id);
            stmt.setString(2, fieldName);
            stmt.setString(3, address);
            stmt.setString(4, city);
            stmt.setString(5, surface.name());
            stmt.setBoolean(6, indoor);
            stmt.setBigDecimal(7, hourlyPrice);
            stmt.setString(8, manager);
            stmt.setString(9, openingHoursJson);

            stmt.execute();
        } catch (SQLException e) {
            throw new PersistenceException("Errore nell'aggiunta del campo", e);
        }
    }

    private static String createJson(Map<DayOfWeek, OpeningTime> openingHours) {
        ObjectMapper mapper = new ObjectMapper();

        List<Map<String, Object>> hours = new ArrayList<>();
        for (var e : openingHours.entrySet()) {
            Map<String, Object> obj = new HashMap<>();
            obj.put("dow", e.getKey().getValue());
            obj.put("open", e.getValue().getOpen().toString());
            obj.put("close", e.getValue().getClose().toString());
            hours.add(obj);
        }

        try {
            return mapper.writeValueAsString(hours);
        } catch (JsonProcessingException e) {
            throw new PersistenceException("Errore serializzazione openingHours in JSON", e);
        }
    }

    @Override
    public void delete(String id) {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (CallableStatement stmt = conn.prepareCall(DELETE_FIELD)){
            stmt.setString(1, id);

            stmt.execute();
        } catch (SQLException e) {
            throw new PersistenceException("Errore nella cancellazione del campo", e);
        }
    }

    @Override
    public List<Field> findFields(String manager) {
        List<Field> fields = new ArrayList<>();

        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (CallableStatement stmt = conn.prepareCall(FIND_FIELDS)) {
            stmt.setString(1, manager);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String id = rs.getString("id");
                String fieldName = rs.getString("fieldName");
                String address = rs.getString("address");
                String city = rs.getString("city");
                SurfaceType surface = SurfaceType.valueOf(rs.getString("surface_type"));

                Field field = new Field(id, fieldName, address, city, surface);
                fields.add(field);
            }
        } catch (SQLException e) {
            throw new PersistenceException("Errore nella ricerca dei campi", e);
        }
        return fields;
    }
}
