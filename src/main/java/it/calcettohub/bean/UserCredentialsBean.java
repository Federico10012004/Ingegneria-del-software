package it.calcettohub.bean;

import it.calcettohub.util.ValidationUtils;

public abstract class UserCredentialsBean {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        String normalized = email.trim().toLowerCase();
        if (ValidationUtils.isValidEmail(normalized)) {
            this.email = normalized;
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
            throw new IllegalArgumentException("La password deve contenere almeno 8 caratteri di cui 1 lettera maiuscola, 1 numero, 1 carattere speciale.");
        }
    }
}
