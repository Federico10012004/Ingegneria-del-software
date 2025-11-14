package it.calcettohub.bean;

import it.calcettohub.util.ValidationUtils;

public class LoginBean {
    private String email;
    private String password;

    public LoginBean() {
        //empty
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (ValidationUtils.isValidEmail(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Email non valida.");
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (ValidationUtils.isValidPassword(password)) {
            this.password = password;
        } else {
            throw new IllegalArgumentException("Password non valida.");
        }
    }
}
