package it.calcettohub.bean;

import java.math.BigDecimal;

public class GetFieldBean {
    private String fieldId;
    private String fieldName;
    private String address;
    private String city;
    private String surface;
    private boolean indoor;
    private BigDecimal hourlyPrice;

    public GetFieldBean(String fieldId, String fieldName, String address, String city, String surface, boolean indoor, BigDecimal hourlyPrice) {
        this.fieldId = fieldId;
        this.fieldName = fieldName;
        this.address = address;
        this.city = city;
        this.surface = surface;
        this.indoor = indoor;
        this.hourlyPrice = hourlyPrice;
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

    public String getSurface() {
        return surface;
    }

    public boolean isIndoor() {
        return indoor;
    }

    public BigDecimal getHourlyPrice() {
        return hourlyPrice;
    }
}