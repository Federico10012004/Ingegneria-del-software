package it.calcettohub.model.valueobject;

import java.time.LocalTime;

public record TimeRange (LocalTime start, LocalTime end){

    public boolean overlaps(TimeRange other) {
        return this.start.isBefore(other.end) && this.end.isAfter(other.start);
    }
}

