package it.calcettohub.bean;

import it.calcettohub.model.Days;
import it.calcettohub.util.ValidationUtils;

import java.time.LocalTime;
import java.util.List;
import java.util.regex.Pattern;

public class RegisterFieldManagerBean extends RegistrationBean {
    private String vatNumber;
    private String structureName;
    private String address;
    private String postalCode;
    private String city;
    private int numFields;
    private List<Days> workingDays;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private String phoneNumber;

    private static final Pattern VAT_PATTERN = Pattern.compile("^[0-9]{11}$");
    private static final Pattern POSTAL_CODE_PATTERN = Pattern.compile("^[0-9]{5}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^(\\+39)?\\s?[0-9]{9,10}$");
    private static final Pattern ADDRESS_PATTERN = Pattern.compile("^[A-Za-zÀ-ÖØ-öø-ÿ'’.\\-\\s]+\\s+\\d+[A-Za-z]?(?:/[A-Za-z0-9])?$");

    public RegisterFieldManagerBean() {}

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public String getStructureName() {
        return structureName;
    }

    public void setStructureName(String structureName) {
        this.structureName = structureName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getNumFields() {
        return numFields;
    }

    public void setNumFields(int numFields) {
        this.numFields = numFields;
    }

    public List<Days> getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(List<Days> workingDays) {
        this.workingDays = workingDays;
    }

    public LocalTime getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(LocalTime openingTime) {
        this.openingTime = openingTime;
    }

    public LocalTime getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(LocalTime closingTime) {
        this.closingTime = closingTime;
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

    private boolean isValidAddress() {
        return ValidationUtils.isNotEmpty(address) && ADDRESS_PATTERN.matcher(address).matches();
    }

    private boolean isValidPostalCode() {
        return ValidationUtils.isNotEmpty(postalCode) && POSTAL_CODE_PATTERN.matcher(postalCode).matches();
    }

    private boolean isValidPhone() {
        return ValidationUtils.isNotEmpty(phoneNumber) && PHONE_PATTERN.matcher(phoneNumber).matches();
    }

    private boolean isValidNumFields() {
        return numFields > 0;
    }

    private boolean isValidWorkingDays() {
        return ValidationUtils.isNotNull(workingDays) && workingDays.isEmpty();
    }

    private boolean isValidWorkingHours() {
        if (openingTime == null || closingTime == null)
            return false;
        return closingTime.isAfter(openingTime);
    }

    @Override
    public boolean validateSpecificFields() {
        return isValidVatNumber()
                && ValidationUtils.isValidName(structureName)
                && isValidAddress()
                && isValidPostalCode()
                && ValidationUtils.isValidName(city)
                && isValidPhone()
                && isValidNumFields()
                && isValidWorkingDays()
                && isValidWorkingHours();
    }
}
