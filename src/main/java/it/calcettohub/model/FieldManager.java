package it.calcettohub.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FieldManager extends User {
    private String vatNumber;
    private List<Field> fields;
    private String phoneNumber;

    public FieldManager (String email, String password, String name, String surname, LocalDate dateOfBirth, String vatNumber, String phoneNumber) {
        super(email, password, Role.FIELDMANAGER, name, surname, dateOfBirth);
        this.vatNumber = vatNumber;
        this.phoneNumber = phoneNumber;
        this.fields = new ArrayList<>();
    }

    public FieldManager (String password, String name, String surname, LocalDate dateOfBirth, String vatNumber, String phoneNumber) {
        super(password, name, surname, dateOfBirth);
        this.vatNumber = vatNumber;
        this.phoneNumber = phoneNumber;
    }

    public String getVatNumber() {
        return vatNumber;
    }

    public void setVatNumber(String vatNumber) {
        this.vatNumber = vatNumber;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    protected List<String> getSpecificFields() {
        return List.of(vatNumber, phoneNumber);
    }
}
