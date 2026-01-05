package it.calcettohub.model.valueobject;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record DateTimeRange(
        LocalDateTime start,
        LocalDateTime end
) {
    public boolean overlaps(DateTimeRange other) {
        return this.start.isBefore(other.end) && other.start.isBefore(this.end);
    }

    public LocalDate date() {
        return start.toLocalDate();
    }
}
