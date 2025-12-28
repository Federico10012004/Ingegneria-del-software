package it.calcettohub.dao;

import it.calcettohub.model.Booking;
import it.calcettohub.model.valueobject.TimeRange;

import java.time.LocalDate;
import java.util.List;

public class BookingDemoDao implements BookingDao {
    private static BookingDemoDao instance;

    public static synchronized BookingDemoDao getInstance() {
        if (instance== null) {
            instance = new BookingDemoDao();
        }
        return instance;
    }

    public List<TimeRange> findByFieldAndDate(String fieldId, LocalDate date) {
        return null;
    }

    public void add(Booking booking) {}

    public void cancellation(String code) {}

    public Booking findByCode(String code) {
        return null;
    }
}
