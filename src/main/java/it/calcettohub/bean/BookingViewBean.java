package it.calcettohub.bean;

import java.time.LocalDate;
import java.time.LocalTime;

public class BookingViewBean {
    private String code;
    private String fieldName;
    private LocalDate date;
    private LocalTime start;
    private LocalTime end;
    private String status;

    public BookingViewBean(String code, String fieldName, LocalDate date, LocalTime start, LocalTime end, String status) {
        this.code = code;
        this.fieldName = fieldName;
        this.date = date;
        this.start = start;
        this.end = end;
        this.status = status;
    }

    public String getBvCode() {
        return code;
    }

    public String getBvFieldName() {
        return fieldName;
    }

    public LocalDate getBvDate() {
        return date;
    }

    public LocalTime getBvStart() {
        return start;
    }

    public LocalTime getBvEnd() {
        return end;
    }

    public String getBvStatus() {
        return status;
    }
}
