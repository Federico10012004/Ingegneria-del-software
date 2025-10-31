package it.calcettohub.bean;

import it.calcettohub.model.PlayerPosition;
import it.calcettohub.util.ValidationUtils;

public class RegisterPlayerBean extends  RegistrationBean {
    private PlayerPosition preferredPosition;

    public RegisterPlayerBean() {}

    public PlayerPosition getPreferredPosition() {
        return preferredPosition;
    }

    public void setPreferredPosition(PlayerPosition preferredPosition) {
        this.preferredPosition = preferredPosition;
    }

    @Override
    public boolean validateSpecificFields() {
        return ValidationUtils.isNotNull(preferredPosition);
    }
}
