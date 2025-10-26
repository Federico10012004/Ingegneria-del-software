package it.calcettohub.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class FieldManager extends User {
    private String vatNumber; // partita IVA
    private String structureName;
    private String address;
    private String postalCode;
    private String city;
    private List <Field> fields;
    private List <Days> workingDays;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private String phoneNumber;

    public FieldManager() {}

    public FieldManager (String email, String password, String name, String surname, LocalDate dateOfBirth, LocalDate registrationDate, String vatNumber, String structureName, String address, String postalCode, String city, List <Field> fields, List <Days> workingDays, LocalTime openingTime, LocalTime closingTime, String phoneNumber) {
        super(email, password, Role.FIELDMANAGER, name, surname, dateOfBirth, registrationDate);
        this.vatNumber = vatNumber;
        this.structureName = structureName;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.fields = fields;
        this.workingDays = workingDays;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.phoneNumber = phoneNumber;
    }

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

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
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
}
