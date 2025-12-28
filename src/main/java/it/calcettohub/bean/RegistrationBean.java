package it.calcettohub.bean;

import it.calcettohub.utils.ValidationUtils;

import java.time.LocalDate;

public abstract class RegistrationBean extends UserCredentialsBean {
    private String confirmPassword;
    private String name;
    private String surname;
    private LocalDate dateOfBirth;

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        if (ValidationUtils.passwordMatch(getPassword(), confirmPassword)) {
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
