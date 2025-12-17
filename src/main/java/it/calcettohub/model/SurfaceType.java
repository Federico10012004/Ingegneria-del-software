package it.calcettohub.model;

public enum SurfaceType {
    SYNTHETIC ("Sintetico"),
    GRASS ("Erba"),
    PARQUET ("Parquet");

    private final String italianName;

    SurfaceType(String italianName) {
        this.italianName = italianName;
    }

    @Override
    public String toString() {
        return italianName;
    }

    public static SurfaceType fromString(String input) {
        for (SurfaceType sur : values()) {
            if (sur.toString().equalsIgnoreCase(input)) {
                return sur;
            }
        }

        return null;
    }
}
