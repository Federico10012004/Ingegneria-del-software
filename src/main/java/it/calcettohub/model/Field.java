package it.calcettohub.model;

public class Field {
    private SurfaceType type;
    private boolean indoor;
    private double hourlyPrice;
    private String address;
    private String city;
    private String postalCode;
    private FieldManager manager;

    public Field() {}

    public Field (SurfaceType type, boolean indoor, double hourlyPrice, String address, String city, String postalCode, FieldManager manager) {
        this.type = type;
        this.indoor = indoor;
        this.hourlyPrice = hourlyPrice;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public FieldManager getManager() {
        return manager;
    }

    public void setManager(FieldManager manager) {
        this.manager = manager;
    }
}
