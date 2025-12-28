package it.calcettohub.bean;

import it.calcettohub.utils.ValidationUtils;

public class FieldManagerAccountBean extends AccountBean {
    private String phoneNumber;

    public FieldManagerAccountBean() {
        //empty
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (ValidationUtils.isValidPhone(phoneNumber)) {
            this.phoneNumber = phoneNumber;
        } else {
            throw new IllegalArgumentException("Numero di telefono non valido.");
        }
    }
}
