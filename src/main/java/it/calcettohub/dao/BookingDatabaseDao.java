package it.calcettohub.dao;

import it.calcettohub.dao.columns.BookingColumns;
import it.calcettohub.dao.columns.FieldColumns;
import it.calcettohub.exceptions.ObjectNotFoundException;
import it.calcettohub.exceptions.PersistenceException;
import it.calcettohub.model.Booking;
import it.calcettohub.model.BookingStatus;
import it.calcettohub.model.valueobject.BookingView;
import it.calcettohub.model.valueobject.TimeRange;
import it.calcettohub.utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class BookingDatabaseDao implements BookingDao {
    private static BookingDatabaseDao instance;
    private static final String FIELD_AVAILABILITY = "{call field_availability(?, ?)}";
    private static final String ADD_BOOKING = "{call add_booking(?, ?, ?, ?, ?, ?, ?)}";
    private static final String CANCEL_BOOKING = "{call cancel_booking(?)}";
    private static final String FIND_BOOKING = "{call find_booking(?)}";
    private static final String FIND_PLAYER_BOOKINGS = "{call find_player_bookings(?)}";
    private static final String FIND_MANAGER_BOOKINGS = "{call find_manager_bookings(?)}";

    public static synchronized BookingDatabaseDao getInstance() {
        if (instance== null) {
            instance = new BookingDatabaseDao();
        }
        return instance;
    }

    @Override
    public List<TimeRange> findByFieldAndDate(String fieldId, LocalDate date) {
        List<TimeRange> busySlots = new ArrayList<>();

        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (CallableStatement stmt = conn.prepareCall(FIELD_AVAILABILITY)) {
            stmt.setString(1, fieldId);
            stmt.setDate(2, Date.valueOf(date));

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                LocalTime start = rs.getTime(BookingColumns.START_TIME).toLocalTime();
                LocalTime end = rs.getTime(BookingColumns.END_TIME).toLocalTime();

                busySlots.add(new TimeRange(start, end));
            }
        } catch (SQLException e) {
            throw new PersistenceException("Errore nell'ottenimento degli slot occupati", e);
        }

        return busySlots;
    }

    @Override
    public void add(Booking booking) {
        String code = booking.getCode();
        String fieldId = booking.getFieldId();
        String playerEmail = booking.getPlayerEmail();
        LocalDate date = booking.getDate();
        LocalTime start = booking.getStartTime();
        LocalTime end = booking.getEndTime();
        BookingStatus status = booking.getStatus();

        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (CallableStatement stmt = conn.prepareCall(ADD_BOOKING)) {
            stmt.setString(1, code);
            stmt.setString(2, fieldId);
            stmt.setString(3, playerEmail);
            stmt.setDate(4, Date.valueOf(date));
            stmt.setTime(5, Time.valueOf(start));
            stmt.setTime(6, Time.valueOf(end));
            stmt.setString(7, status.name());

            stmt.execute();
        } catch (SQLException e) {
            throw new PersistenceException("Errore nell'aggiunta della prenotazione", e);
        }
    }

    @Override
    public void cancellation(String code) {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (CallableStatement stmt = conn.prepareCall(CANCEL_BOOKING)) {
            stmt.setString(1, code);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenceException("Errore durante la cancellazione della prenotazione", e);
        }
    }

    @Override
    public Booking findByCode(String code) {
        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (CallableStatement stmt = conn.prepareCall(FIND_BOOKING)) {
            stmt.setString(1, code);

            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                throw new ObjectNotFoundException("Prenotazione non trovata.");
            }

            String fieldId = rs.getString(BookingColumns.FIELD_ID);
            String playerEmail = rs.getString(BookingColumns.PLAYER_EMAIL);
            LocalDate date = rs.getDate(BookingColumns.BOOKING_DATE).toLocalDate();
            LocalTime startTime = rs.getTime(BookingColumns.START_TIME).toLocalTime();
            LocalTime endTime = rs.getTime(BookingColumns.END_TIME).toLocalTime();
            BookingStatus status = BookingStatus.valueOf(rs.getString(BookingColumns.STATUS));

            return new Booking(code, fieldId, playerEmail, new TimeRange(startTime, endTime).onDate(date), status);
        } catch (SQLException e) {
            throw new PersistenceException("Errore nella ricerca della prenotazione ", e);
        }
    }

    @Override
    public List<BookingView> findPlayerBookings(String playerEmail) {
        List<BookingView> playerBookings = new ArrayList<>();

        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (CallableStatement stmt = conn.prepareCall(FIND_PLAYER_BOOKINGS)) {
            stmt.setString(1, playerEmail);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String code = rs.getString(BookingColumns.CODE);
                String fieldName = rs.getString(FieldColumns.FIELD_NAME);
                LocalDate date = rs.getDate(BookingColumns.BOOKING_DATE).toLocalDate();
                LocalTime startTime = rs.getTime(BookingColumns.START_TIME).toLocalTime();
                LocalTime endTime = rs.getTime(BookingColumns.END_TIME).toLocalTime();
                BookingStatus status = BookingStatus.valueOf(rs.getString(BookingColumns.STATUS));

                BookingView booking = new BookingView(code, fieldName, new TimeRange(startTime, endTime).onDate(date), status);
                playerBookings.add(booking);
            }
        } catch (SQLException e) {
            throw new PersistenceException("Errore nell'ottenimento delle prenotazioni del giocatore", e);
        }

        return playerBookings;
    }

    @Override
    public List<BookingView> findManagerBookings(String fieldManagerEmail) {
        List<BookingView> managerBookings = new ArrayList<>();

        Connection conn = DatabaseConnection.getInstance().getConnection();
        try (CallableStatement stmt = conn.prepareCall(FIND_MANAGER_BOOKINGS)) {
            stmt.setString(1, fieldManagerEmail);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String code = rs.getString(BookingColumns.CODE);
                String fieldName = rs.getString(FieldColumns.FIELD_NAME);
                LocalDate date = rs.getDate(BookingColumns.BOOKING_DATE).toLocalDate();
                LocalTime startTime = rs.getTime(BookingColumns.START_TIME).toLocalTime();
                LocalTime endTime = rs.getTime(BookingColumns.END_TIME).toLocalTime();
                BookingStatus status = BookingStatus.valueOf(rs.getString(BookingColumns.STATUS));

                BookingView booking = new BookingView(code, fieldName, new TimeRange(startTime, endTime).onDate(date), status);
                managerBookings.add(booking);
            }
        } catch (SQLException e) {
            throw new PersistenceException("Errore nell'ottenimento delle prenotazioni associate al field manager", e);
        }

        return managerBookings;
    }
}