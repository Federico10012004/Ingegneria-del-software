package it.calcettohub.model;

import java.time.LocalDate;

public class Player extends User {
    private PlayerPosition preferredPosition;

    public Player() {
        // costruttore vuoto JavaBean
    }

    public Player (String email, String password, String name, String surname, LocalDate dateOfBirth, LocalDate registrationDate, PlayerPosition preferredPosition) {
        super(email, password, Role.PLAYER, name, surname, dateOfBirth, registrationDate);
        this.preferredPosition = preferredPosition;
    }

    public PlayerPosition getPreferredPosition() {
        return preferredPosition;
    }

    public void setPreferredPosition(PlayerPosition preferredPosition) {
        this.preferredPosition = preferredPosition;
    }
}
