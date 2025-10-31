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

    public RegistrationBean() {}

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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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

    protected final boolean validateCommonFields() {
        return ValidationUtils.isValidEmail(email)
                && ValidationUtils.isValidPassword(password)
                && ValidationUtils.passwordMatch(password, confirmPassword)
                && ValidationUtils.isValidName(name)
                && ValidationUtils.isValidName(surname)
                && ValidationUtils.isValidAge(dateOfBirth);
    }

    public abstract boolean validateSpecificFields();

    public final boolean isValid() {
        return validateCommonFields() && validateSpecificFields();
    }
}
