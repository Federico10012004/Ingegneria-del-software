package it.calcettohub.bean;

import it.calcettohub.model.valueobject.TimeRange;
import it.calcettohub.utils.ValidationUtils;

import java.time.LocalDate;

public class BookingBean {
    private String fieldId;
    private LocalDate date;
    private TimeRange slot;

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

    public TimeRange getSlot() {
        return slot;
    }

    public void setSlot(TimeRange slot) {
        if (ValidationUtils.isNotNull(slot)) {
            this.slot = slot;
        } else {
            throw new IllegalArgumentException("Seleziona uno slot valido.");
        }
    }
}
