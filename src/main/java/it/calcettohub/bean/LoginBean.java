package it.calcettohub.bean;

import it.calcettohub.model.Role;
import it.calcettohub.util.ValidationUtils;

public class LoginBean {
    private String email;
    private String password;
    private Role role;

    public LoginBean() {
        //empty
    }

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
            throw new IllegalArgumentException("La password deve contenere almeno 8 caratteri di cui 1 lettera maiuscola, 1 numero, 1 carattere speciale.");
        }
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
