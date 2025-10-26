package it.calcettohub.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Booking {
    private Field field;
    private Player player;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean confirmed;

    public Booking() {}

    public Booking (Field field, Player player, LocalDate date, LocalTime startTime, LocalTime endTime, boolean confirmed) {
        this.field = field;
        this.player = player;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.confirmed = confirmed;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
}
