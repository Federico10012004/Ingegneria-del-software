package it.calcettohub.model;

public enum BookingStatus {
    CONFIRMED("Aperta"),
    CANCELLED("Disdetta"),
    COMPLETED("Terminata");

    private final String italianName;

    BookingStatus(String italianName) {
        this.italianName = italianName;
    }

    public boolean allowsCancellation() {
        return this == CONFIRMED;
    }

    @Override
    public String toString() {
        return italianName;
    }
}
