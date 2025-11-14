package it.calcettohub.bean;

import it.calcettohub.util.ValidationUtils;

import java.util.regex.Pattern;

public class RegisterFieldManagerBean extends RegistrationBean {
    private String vatNumber;
    private int numFields;
    private String phoneNumber;

    private static final Pattern VAT_PATTERN = Pattern.compile("^\\d{11}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^(\\+39)?\\s?\\d{9,10}$");

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

    public int getNumFields() {
        return numFields;
    }

    public void setNumFields(int numFields) {
        if (numFields > 0) {
            this.numFields = numFields;
        } else {
            throw new IllegalArgumentException("Numero campi non valido.");
        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (ValidationUtils.isNotNull(phoneNumber) && PHONE_PATTERN.matcher(phoneNumber).matches()) {
            this.phoneNumber = phoneNumber;
        } else {
            throw new IllegalArgumentException("Numero di telefono non valido.");
        }
    }
}
