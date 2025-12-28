package it.calcettohub.bean;

import it.calcettohub.model.PlayerPosition;
import it.calcettohub.utils.ValidationUtils;

public class PlayerAccountBean extends AccountBean {
    private PlayerPosition position;

    public PlayerAccountBean() {
        // empty
    }

    public PlayerPosition getPosition() {
        return position;
    }

    public void setPosition(PlayerPosition position) {
        if (ValidationUtils.isNotNull(position)) {
            this.position = position;
        } else {
            throw new IllegalArgumentException("Posizione non valida.");
        }
    }
}
