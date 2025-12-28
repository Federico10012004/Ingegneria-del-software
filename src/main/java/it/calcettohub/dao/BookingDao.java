package it.calcettohub.dao;

import it.calcettohub.model.Booking;
import it.calcettohub.model.valueobject.TimeRange;

import java.time.LocalDate;
import java.util.List;

public interface BookingDao {
    List<TimeRange> findByFieldAndDate(String fieldId, LocalDate date);
    void add(Booking booking);
    public void cancellation(String code);
    public Booking findByCode(String code);
}