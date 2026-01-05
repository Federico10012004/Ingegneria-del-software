package it.calcettohub.model;

import it.calcettohub.model.valueobject.DateTimeRange;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

public class Booking {
    private final String code;
    private String fieldId;
    private String playerEmail;
    private DateTimeRange slot;
    private BookingStatus status;

    public Booking (String fieldId, String playerEmail, DateTimeRange slot) {
        this.code = UUID.randomUUID().toString();
        this.fieldId = fieldId;
        this.playerEmail = playerEmail;
        this.slot = slot;
        this.status = BookingStatus.CONFIRMED;
    }

    public Booking (String code, String fieldId, String playerEmail, DateTimeRange slot, BookingStatus status) {
        this.code = code;
        this.fieldId = fieldId;
        this.playerEmail = playerEmail;
        this.slot = slot;
        this.status = status;
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
        return slot.start().toLocalDate();
    }

    public LocalTime getStartTime() {
        return slot.start().toLocalTime();
    }

    public LocalTime getEndTime() {
        return slot.end().toLocalTime();
    }

    public DateTimeRange getSlot() {
        return slot;
    }

    public void setSlot(DateTimeRange slot) {
        this.slot = slot;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public boolean isCancelable(LocalDateTime now) {
        if (!status.allowsCancellation()) return false;
        return now.isBefore(slot.end());
    }
}
