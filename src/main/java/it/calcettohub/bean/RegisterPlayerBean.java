package it.calcettohub.bean;

import it.calcettohub.model.PlayerPosition;
import it.calcettohub.utils.ValidationUtils;

public class RegisterPlayerBean extends  RegistrationBean {
    private PlayerPosition preferredPosition;

    public RegisterPlayerBean() {
        //empty
    }

    public PlayerPosition getPreferredPosition() {
        return preferredPosition;
    }

    public void setPreferredPosition(PlayerPosition preferredPosition) {
        if (ValidationUtils.isNotNull(preferredPosition)) {
            this.preferredPosition = preferredPosition;
        } else {
            throw new IllegalArgumentException("Posizione non valida.");
        }
    }
}
