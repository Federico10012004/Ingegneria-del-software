package it.calcettohub.model;

import java.time.LocalTime;

public class Field {
    private SurfaceType type;
    private boolean indoor;
    private double hourlyPrice;
    private boolean isActive; // per gestire ristrutturazioni
    private FieldManager manager;

    public Field() {}

    public Field (SurfaceType type, boolean indoor, double hourlyPrice, FieldManager manager) {
        this.type = type;
        this.indoor = indoor;
        this.hourlyPrice = hourlyPrice;
        this.isActive = true;
        this.manager = manager;
    }

    public SurfaceType getType() {
        return type;
    }

    public void setType(SurfaceType type) {
        this.type = type;
    }

    public boolean isIndoor() {
        return indoor;
    }

    public void setIndoor(boolean indoor) {
        this.indoor = indoor;
    }

    public double getHourlyPrice() {
        return hourlyPrice;
    }

    public void setHourlyPrice(double hourlyPrice) {
        this.hourlyPrice = hourlyPrice;
    }

    public boolean isActive() { return isActive; }

    public void setActive(boolean active) { isActive = active; }

    public FieldManager getManager() {
        return manager;
    }

    public void setManager(FieldManager manager) {
        this.manager = manager;
    }

    public String getAddress() {
        return manager != null ? manager.getAddress() : null;
    }

    public String getCity() {
        return manager != null ? manager.getCity() : null;
    }

    public String gePostalCode() {
        return manager != null ? manager.getPostalCode() : null;
    }

    public LocalTime getOpeningTime() {
        return (manager != null) ? manager.getOpeningTime() : null;
    }

    public LocalTime getClosingTime() {
        return (manager != null) ? manager.getClosingTime() : null;
    }
}
