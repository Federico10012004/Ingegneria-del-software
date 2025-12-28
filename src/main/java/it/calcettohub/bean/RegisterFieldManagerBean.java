package it.calcettohub.bean;

import it.calcettohub.utils.ValidationUtils;

import java.util.regex.Pattern;

public class RegisterFieldManagerBean extends RegistrationBean {
    private String vatNumber;
    private String phoneNumber;

    private static final Pattern VAT_PATTERN = Pattern.compile("^\\d{11}$");

    public RegisterFieldManagerBean() {
        //empty
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        if (ValidationUtils.isNotNull(vatNumber) && VAT_PATTERN.matcher(vatNumber).matches()) {
            this.vatNumber = vatNumber;
        } else {
            throw new IllegalArgumentException("Partita IVA non valida.");
        }
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
