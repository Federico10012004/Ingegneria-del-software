package it.calcettohub.bean;

import it.calcettohub.utils.ValidationUtils;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingBean {
    private String fieldId;
    private LocalDate date;
    private LocalTime start;
    private LocalTime end;

    public BookingBean() {
        // empty
    }

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        if (ValidationUtils.isNotEmpty(fieldId)) {
            this.fieldId = fieldId;
        } else {
            throw new IllegalArgumentException("Id campo non valido.");
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        if (ValidationUtils.isPastBookingDate(date)) {
            throw new IllegalArgumentException("La data di prenotazione non può essere nel passato.");
        }

        this.date = date;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }
}
