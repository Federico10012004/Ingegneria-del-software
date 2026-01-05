package it.calcettohub.bean;

import it.calcettohub.model.valueobject.DateTimeRange;
import it.calcettohub.utils.ValidationUtils;

public class BookingBean {
    private String fieldId;
    private DateTimeRange slot;

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

    public DateTimeRange getSlot() {
        return slot;
    }

    public void setSlot(DateTimeRange slot) {
        if (!ValidationUtils.isNotNull(slot)) {
            throw new IllegalArgumentException("Seleziona uno slot valido.");
        }

        if (ValidationUtils.isPastBookingDate(slot.date())) {
            throw new IllegalArgumentException("La data di prenotazione non può essere nel passato.");
        }

        this.slot = slot;
    }
}
