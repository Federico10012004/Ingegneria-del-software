package it.calcettohub.bean;

import it.calcettohub.utils.ValidationUtils;

public class CancelBookingBean {
    private String bookingCode;
    private String reason;

    public CancelBookingBean() {
        // empty
    }

    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        if (ValidationUtils.isNotEmpty(bookingCode)) {
            this.bookingCode = bookingCode;
        } else {
            throw new IllegalArgumentException("Codice della prenotazione non valido.");
        }
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        if (ValidationUtils.isNotEmpty(reason)) {
            this.reason = reason;
        } else {
            throw new IllegalArgumentException("Inserire motivazione della disdetta.");
        }
    }
}
