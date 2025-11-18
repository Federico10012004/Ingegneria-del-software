package it.calcettohub.bean;

import it.calcettohub.util.ValidationUtils;

import java.time.LocalDate;

public abstract class RegistrationBean {
    private String email;
    private String password;
    private String confirmPassword;
    private String name;
    private String surname;
    private LocalDate dateOfBirth;

    protected RegistrationBean() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (ValidationUtils.isValidEmail(email.trim().toLowerCase())) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Formato email non valido.");
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (ValidationUtils.isValidPassword(password)) {
            this.password = password;
        } else {
            throw new IllegalArgumentException("Formato password non valido.");
        }
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        if (ValidationUtils.passwordMatch(password, confirmPassword)) {
            this.confirmPassword = confirmPassword;
        } else {
            throw new IllegalArgumentException("Le password inserite sono differenti.");
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (ValidationUtils.isNotEmpty(name)) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Nome non inserito.");
        }
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        if (ValidationUtils.isNotEmpty(surname)) {
            this.surname = surname;
        } else {
            throw new IllegalArgumentException("Cognome non inserito.");
        }
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        if (ValidationUtils.isValidDate(dateOfBirth)) {
            this.dateOfBirth = dateOfBirth;
        } else {
            throw new IllegalArgumentException("La data di nascita non può essere successiva ad oggi.");
        }
    }
}
