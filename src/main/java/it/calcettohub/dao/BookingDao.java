package it.calcettohub.dao;

import it.calcettohub.model.Booking;
import it.calcettohub.model.valueobject.BookingView;
import it.calcettohub.model.valueobject.TimeRange;

import java.time.LocalDate;
import java.util.List;

public interface BookingDao {
    List<TimeRange> findByFieldAndDate(String fieldId, LocalDate date);
    void add(Booking booking);
    void cancellation(String code);
    Booking findByCode(String code);
    List<BookingView> findPlayerBookings(String playerEmail);
    List<BookingView> findManagerBookings(String fieldManagerEmail);
}