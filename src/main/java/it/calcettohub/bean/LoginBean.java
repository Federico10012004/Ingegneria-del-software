package it.calcettohub.bean;

import it.calcettohub.model.Role;

public class LoginBean extends UserCredentialsBean {
    private Role role;

    public LoginBean() {
        //empty
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
