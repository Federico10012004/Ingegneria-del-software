package it.calcettohub.dao;

import it.calcettohub.exceptions.ObjectNotFoundException;
import it.calcettohub.model.Booking;
import it.calcettohub.model.BookingStatus;
import it.calcettohub.model.Field;
import it.calcettohub.model.valueobject.BookingView;
import it.calcettohub.model.valueobject.TimeRange;
import it.calcettohub.utils.DemoRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class BookingDemoDao implements BookingDao {
    private static BookingDemoDao instance;
    private final Map<String, Booking> bookings;
    private final Map<String, Field> fields;

    public BookingDemoDao() {
        this.bookings = DemoRepository.getInstance().getBookings();
        this.fields = DemoRepository.getInstance().getFields();
    }

    public static synchronized BookingDemoDao getInstance() {
        if (instance== null) {
            instance = new BookingDemoDao();
        }
        return instance;
    }

    @Override
    public List<TimeRange> findByFieldAndDate(String fieldId, LocalDate date) {
        List<TimeRange> busySlots = new ArrayList<>();
        for (Booking b : bookings.values()) {
            if (fieldId.equals(b.getFieldId()) && date.equals(b.getDate()) && b.getStatus() == BookingStatus.CONFIRMED) {
                busySlots.add(new TimeRange(b.getStartTime(), b.getEndTime()));
            }
        }

        return busySlots;
    }

    @Override
    public void add(Booking booking) {
        String code = booking.getCode();
        bookings.put(code, booking);
    }

    @Override
    public void cancellation(String code) {
        Booking booking = bookings.get(code);
        booking.setStatus(BookingStatus.CANCELLED);
    }

    @Override
    public Booking findByCode(String code) {
        Booking booking = bookings.get(code);
        if (booking == null) {
            throw new ObjectNotFoundException("Prenotazione non trovata.");
        }

        return booking;
    }

    @Override
    public List<BookingView> findPlayerBookings(String playerEmail) {
        LocalDateTime now = LocalDateTime.now();

        List<BookingView> playerBookings = new ArrayList<>();

        for (Booking b : bookings.values()) {
            if (!playerEmail.equals(b.getPlayerEmail())) continue;

            if (b.getStatus() == BookingStatus.CONFIRMED && !b.getSlot().end().isAfter(now)) {
                b.setStatus(BookingStatus.COMPLETED);
            }

            String fieldName = fields.get(b.getFieldId()).getFieldName();

            BookingView booking = new BookingView(b.getCode(), fieldName, b.getSlot(), b.getStatus());
            playerBookings.add(booking);
        }

        playerBookings.sort(Comparator
                .comparing(BookingView::fieldName, String.CASE_INSENSITIVE_ORDER)
                .thenComparing(bv -> bv.slot().start()));

        return playerBookings;
    }

    @Override
    public List<BookingView> findManagerBookings(String fieldManagerEmail) {
        List<BookingView> managerBookings = new ArrayList<>();

        for (Booking b : bookings.values()) {
            Field field = fields.get(b.getFieldId());

            if (!fieldManagerEmail.equals(field.getManager())) continue;

            BookingView booking = new BookingView(b.getCode(), field.getFieldName(), b.getSlot(), b.getStatus());
            managerBookings.add(booking);
        }

        managerBookings.sort(Comparator
                .comparing(BookingView::fieldName, String.CASE_INSENSITIVE_ORDER)
                .thenComparing(bv -> bv.slot().start()));

        return managerBookings;
    }
}
