package it.calcettohub.bean;

public class FieldMapBean {
    private String fieldId;
    private String fieldName;
    private String address;
    private String city;
    private Double lat;
    private Double lon;

    public FieldMapBean(String fieldId, String fieldName, String address, String city, Double lat, Double lon) {
        this.fieldId = fieldId;
        this.fieldName = fieldName;
        this.address = address;
        this.city = city;
        this.lat = lat;
        this.lon = lon;
    }

    public String getFieldId() {
        return fieldId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLon() {
        return lon;
    }
}