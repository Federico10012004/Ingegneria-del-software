package it.calcettohub.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Field {
    private String fieldName;
    private SurfaceType surface;
    private String address;
    private String city;
    private Map<DayOfWeek, LocalTime[]> openingHours;
    private List<Booking> bookings;
    private boolean indoor;
    private double hourlyPrice;
    private boolean isActive; // per gestire ristrutturazioni
    private FieldManager manager;

    public Field() {}

    public Field (String fieldName, SurfaceType surface, String address, String city, boolean indoor, double hourlyPrice, FieldManager manager) {
        this.fieldName = fieldName;
        this.surface = surface;
        this.address = address;
        this.city = city;
        this.openingHours = new EnumMap<>(DayOfWeek.class);
        this.bookings = new ArrayList<>();
        this.indoor = indoor;
        this.hourlyPrice = hourlyPrice;
        this.isActive = true;
        this.manager = manager;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public SurfaceType getSurfaceType() {
        return surface;
    }

    public void setSurfaceType(SurfaceType surface) {
        this.surface = surface;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public Map<DayOfWeek, LocalTime[]> getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(DayOfWeek day, LocalTime open, LocalTime close) {
        openingHours.put(day, new LocalTime[]{open, close});
    }

    // --- DISPONIBILITÀ DINAMICA ---

    /**
     * Calcola le fasce orarie ancora libere in un determinato giorno,
     * in base alla durata delle partite (slotDurationMinutes).
     */
    // DA RICONTROLLARE SE LOGICA TROPPO COMPLESSA PER UN MODEL
    public List<LocalTime[]> getAvailableSlots(LocalDate date, int slotDurationMinutes) {
        DayOfWeek day = date.getDayOfWeek();
        LocalTime[] hours = openingHours.get(day);
        if (hours == null) return List.of(); // giorno non lavorativo

        LocalTime open = hours[0];
        LocalTime close = hours[1];

        List<LocalTime[]> freeSlots = new ArrayList<>();
        LocalTime current = open;

        while (current.plusMinutes(slotDurationMinutes).isBefore(close) ||
                current.plusMinutes(slotDurationMinutes).equals(close)) {

            LocalTime end = current.plusMinutes(slotDurationMinutes);

            LocalTime finalCurrent = current;
            boolean occupied = bookings.stream()
                    .anyMatch(b -> b.getDate().equals(date) &&
                            !(end.isBefore(b.getStartTime()) || finalCurrent.isAfter(b.getEndTime())));

            if (!occupied) {
                freeSlots.add(new LocalTime[]{current, end});
            }

            current = end;
        }

        return freeSlots;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isIndoor() {
        return indoor;
    }

    public void setIndoor(boolean indoor) {
        this.indoor = indoor;
    }

    public double getHourlyPrice() {
        return hourlyPrice;
    }

    public void setHourlyPrice(double hourlyPrice) {
        this.hourlyPrice = hourlyPrice;
    }

    public boolean isActive() { return isActive; }

    public void setActive(boolean active) { isActive = active; }

    public FieldManager getManager() {
        return manager;
    }

    public void setManager(FieldManager manager) {
        this.manager = manager;
    }
}
