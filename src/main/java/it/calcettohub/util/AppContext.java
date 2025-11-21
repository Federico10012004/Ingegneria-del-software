package it.calcettohub.util;

import it.calcettohub.model.Role;

public class AppContext {
    private static Role selectedRole;

    private AppContext() {}

    public static Role getSelectedRole() {
        return selectedRole;
    }

    public static void setSelectedRole(Role role) {
        selectedRole = role;
    }
}
