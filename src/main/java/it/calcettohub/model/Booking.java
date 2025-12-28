package it.calcettohub.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class Booking {
    private final String code;
    private String fieldId;
    private String playerEmail;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String status;

    public Booking (String fieldId, String playerEmail, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.code = UUID.randomUUID().toString();
        this.fieldId = fieldId;
        this.playerEmail = playerEmail;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = "APERTA";
    }

    public Booking (String code, String fieldId, String playerEmail, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.code = code;
        this.fieldId = fieldId;
        this.playerEmail = playerEmail;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getCode() {
        return code;
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setField(String fieldId) {
        this.fieldId = fieldId;
    }

    public String getPlayerEmail() {
        return playerEmail;
    }

    public void setPlayer(String playerEmail) {
        this.playerEmail = playerEmail;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
