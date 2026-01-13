package it.calcettohub.bean;

import it.calcettohub.utils.ValidationUtils;

import java.time.LocalDate;

public class FreeSlotsBean {
    private String fieldId;
    private LocalDate date;

    public FreeSlotsBean() {}

    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        if (ValidationUtils.isNotNull(fieldId)) {
            this.fieldId = fieldId;
        } else {
            throw new IllegalArgumentException("Field id non può essere nullo.");
        }
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        if (ValidationUtils.isNotNull(date)) {
            this.date = date;
        } else {
            throw new IllegalArgumentException("Data non inserita.");
        }
    }
}
