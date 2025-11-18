package it.calcettohub.model;

public enum PlayerPosition {
    GOALKEEPER ("Portiere"),
    DEFENDER ("Difensore"),
    MIDFIELDER ("Centrocampista"),
    STRIKER ("Attaccante");

    private final String italianName;

    PlayerPosition(String italianName) {
        this.italianName = italianName;
    }

    @Override
    public String toString() {
        return italianName;
    }

    public static PlayerPosition fromString(String input) {
        for (PlayerPosition pos : values()) {
            if (pos.toString().equalsIgnoreCase(input)) {
                return pos;
            }
        }
        return null;
    }
}
