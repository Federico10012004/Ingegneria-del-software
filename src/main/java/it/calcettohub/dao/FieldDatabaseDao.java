package it.calcettohub.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.calcettohub.dao.columns.FieldColumns;
import it.calcettohub.exceptions.ObjectNotFoundException;
import it.calcettohub.exceptions.PersistenceException;
import it.calcettohub.model.Field;
import it.calcettohub.model.valueobject.TimeRange;
import it.calcettohub.model.SurfaceType;
import it.calcettohub.utils.DatabaseConnection;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

public class FieldDatabaseDao implements FieldDao {
    private static FieldDatabaseDao instance;
    private static final String ADD_FIELD = "{call add_field(?, ?, ?, ?, ?, ?, ?, ?, ?)}";
    private static final String FIND_FIELDS = "{call find_fields_by_manager(?)}";
    private static final String DELETE_FIELD = "{call delete_field(?)}";
    private static final String SEARCH_FIELDS = "{call search_fields(?, ?)}";
    private static final String FIND_BY_ID = "{call find_field_by_id(?)}";

    public static synchronized FieldDatabaseDao getInstance() {
        if (instance== null) {
            instance = new FieldDatabaseDao();
        }
        return instance;
    }

    @Override
    public void add(Field field) {
        String id = field.getId();
        String field_name = field.getFieldName();
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
            stmt.setString(2, field_name);
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

    private static String createJson(Map<DayOfWeek, TimeRange> openingHours) {
        ObjectMapper mapper = new ObjectMapper();

        List<Map<String, Object>> hours = new ArrayList<>();
        for (var e : openingHours.entrySet()) {
            Map<String, Object> obj = new HashMap<>();
            obj.put("dow", e.getKey().getValue());
            obj.put("open", e.getValue().start().toString());
            obj.put("close", e.getValue().end().toString());
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
    public List<Field> findFieldsByManager(String manager) {
        List<Field> fields = new ArrayList<>();

        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (CallableStatement stmt = conn.prepareCall(FIND_FIELDS)) {
            stmt.setString(1, manager);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String id = rs.getString(FieldColumns.ID);
                String field_name = rs.getString(FieldColumns.FIELD_NAME);
                String address = rs.getString(FieldColumns.ADDRESS);
                String city = rs.getString(FieldColumns.CITY);
                SurfaceType surface = SurfaceType.valueOf(rs.getString(FieldColumns.SURFACE_TYPE));
                boolean indoor = rs.getBoolean(FieldColumns.INDOOR);
                BigDecimal hourlyPrice = rs.getBigDecimal(FieldColumns.HOURLY_PRICE);

                Field field = new Field(id, field_name, address, city, surface, indoor, hourlyPrice);
                fields.add(field);
            }
        } catch (SQLException e) {
            throw new PersistenceException("Errore nella ricerca dei campi", e);
        }
        return fields;
    }

    @Override
    public List<Field> searchFields(String fieldAddress, String fieldCity) {
        List<Field> fields = new ArrayList<>();

        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (CallableStatement stmt = conn.prepareCall(SEARCH_FIELDS)) {
            stmt.setString(1, fieldAddress);
            stmt.setString(2, fieldCity);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String id = rs.getString(FieldColumns.ID);
                String field_name = rs.getString(FieldColumns.FIELD_NAME);
                String address = rs.getString(FieldColumns.ADDRESS);
                String city = rs.getString(FieldColumns.CITY);
                SurfaceType surface = SurfaceType.valueOf(rs.getString(FieldColumns.SURFACE_TYPE));
                boolean indoor = rs.getBoolean(FieldColumns.INDOOR);
                BigDecimal hourlyPrice = rs.getBigDecimal(FieldColumns.HOURLY_PRICE);

                Field field = new Field(id, field_name, address, city, surface, indoor, hourlyPrice);
                fields.add(field);
            }
        } catch (SQLException e) {
            throw new PersistenceException("Errore nella ricerca dei campi desiderati", e);
        }

        return fields;
    }

    @Override
    public Field findById(String id) {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (CallableStatement stmt = conn.prepareCall(FIND_BY_ID)) {
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                throw new ObjectNotFoundException("Campo non trovato.");
            }

            String field_name = rs.getString(FieldColumns.FIELD_NAME);
            String address = rs.getString(FieldColumns.ADDRESS);
            String city = rs.getString(FieldColumns.CITY);
            SurfaceType surface = SurfaceType.valueOf(rs.getString(FieldColumns.SURFACE_TYPE));
            boolean indoor = rs.getBoolean(FieldColumns.INDOOR);
            BigDecimal hourlyPrice = rs.getBigDecimal(FieldColumns.HOURLY_PRICE);
            String manager = rs.getString(FieldColumns.MANAGER);

            Map<DayOfWeek, TimeRange> openingHours = new EnumMap<>(DayOfWeek.class);

            do {
                int dowInt = rs.getInt(FieldColumns.DAY_OF_WEEK);
                DayOfWeek dow = DayOfWeek.of(dowInt);

                LocalTime open = rs.getTime(FieldColumns.OPENING_TIME).toLocalTime();
                LocalTime close = rs.getTime(FieldColumns.CLOSING_TIME).toLocalTime();

                openingHours.put(dow, new TimeRange(open, close));
            } while (rs.next());

            return new Field(id, field_name, address, city, surface, openingHours, indoor, hourlyPrice, manager);
        } catch (SQLException e) {
            throw new PersistenceException("Errore nella ricerca del campo", e);
        }
    }
}
