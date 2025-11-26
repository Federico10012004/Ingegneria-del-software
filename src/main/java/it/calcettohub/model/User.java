package it.calcettohub.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class User {
    private String email;
    private String password;
    private Role role;
    private String name;
    private String surname;
    private LocalDate dateOfBirth;

    protected User (String email, String password, Role role, String name, String surname, LocalDate dateOfBirth) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
    }

    protected User (String email, String name, String surname, LocalDate dateOfBirth) {
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    protected List<String> getCommonFields() {
        return List.of(email, password, name, surname, dateOfBirth.toString());
    }

    protected abstract List<String> getSpecificFields();

    public final List<String> getAllFields() {
        List<String> fields = new ArrayList<>(getCommonFields());
        fields.addAll(getSpecificFields());
        return fields;
    }
}
