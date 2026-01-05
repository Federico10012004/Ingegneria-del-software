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

    /*public static BookingStatus fromString(String input) {
        for (BookingStatus bs : values()) {
            if (bs.toString().equalsIgnoreCase(input)) {
                return bs;
            }
        }

        return null;
    }*/
}
