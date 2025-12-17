package it.calcettohub.model;

import java.time.LocalTime;

public class OpeningTime {
    private LocalTime open;
    private LocalTime close;

    public OpeningTime(LocalTime open, LocalTime close) {
        this.open = open;
        this.close = close;
    }

    public LocalTime getOpen() { return open; }
    public LocalTime getClose() { return close; }
}

