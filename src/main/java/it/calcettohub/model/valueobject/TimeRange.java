package it.calcettohub.model.valueobject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record TimeRange (
        LocalTime start,
        LocalTime end
){
    public DateTimeRange onDate(LocalDate date) {
        LocalDateTime s = date.atTime(start);
        LocalDateTime e = date.atTime(end);
        if (!end.isAfter(start)) {
            e = e.plusDays(1);
        }
        return new DateTimeRange(s, e);
    }
}

