package it.calcettohub.bean;

import it.calcettohub.util.ValidationUtils;

import java.time.LocalDate;

public abstract class AccountBean {
    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private String password;
    private String confirmPassword;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (ValidationUtils.isValidPassword(password)) {
            this.password = password;
        } else {
            throw new IllegalArgumentException("La password deve contenere almeno 8 caratteri di cui 1 lettera maiuscola, 1 numero, 1 carattere speciale.");
        }
    }

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
}
