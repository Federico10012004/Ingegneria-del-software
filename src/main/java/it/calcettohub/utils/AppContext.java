package it.calcettohub.utils;

import it.calcettohub.model.Role;

public class AppContext {
    private static Role selectedRole;
    private static String fieldId;

    private AppContext() {}

    public static Role getSelectedRole() {
        return selectedRole;
    }

    public static void setSelectedRole(Role role) {
        selectedRole = role;
    }

    public static void resetUserRole() {
        selectedRole = null;
    }

    public static String getFieldId() {
        return fieldId;
    }

    public static void setFieldId(String id) {
        fieldId = id;
    }
}