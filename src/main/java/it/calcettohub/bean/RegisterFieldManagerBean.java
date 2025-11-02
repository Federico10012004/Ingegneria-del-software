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
        this.vatNumber = vatNumber;
    }

    public int getNumFields() {
        return numFields;
    }

    public void setNumFields(int numFields) {
        this.numFields = numFields;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    private boolean isValidVatNumber() {
        return ValidationUtils.isNotEmpty(vatNumber) && VAT_PATTERN.matcher(vatNumber).matches();
    }

    private boolean isValidPhone() {
        return ValidationUtils.isNotEmpty(phoneNumber) && PHONE_PATTERN.matcher(phoneNumber).matches();
    }

    private boolean isValidNumFields() {
        return numFields > 0;
    }

    @Override
    public boolean validateSpecificFields() {
        return isValidVatNumber()
                && isValidPhone()
                && isValidNumFields();
    }
}
