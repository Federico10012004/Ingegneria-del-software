package it.calcettohub.model;

import it.calcettohub.model.valueobject.TimeRange;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

public class Field {
    private final String id;
    private String fieldName;
    private String address;
    private String city;
    private SurfaceType surface;
    private Map<DayOfWeek, TimeRange> openingHours;
    private boolean indoor;
    private BigDecimal hourlyPrice;
    private String emailManager;

    public Field (String fieldName, String address, String city,
                  SurfaceType surface, Map<DayOfWeek, TimeRange> openingHours,
                  boolean indoor, BigDecimal hourlyPrice, String emailManager) {
        this.id = UUID.randomUUID().toString();
        this.fieldName = fieldName;
        this.address = address;
        this.city = city;
        this.surface = surface;
        this.openingHours = new EnumMap<>(openingHours);
        this.indoor = indoor;
        this.hourlyPrice = hourlyPrice;
        this.emailManager = emailManager;
    }

    public Field (String id, String fieldName, String address, String city,
                  SurfaceType surface, boolean indoor, BigDecimal hourlyPrice) {
        this.id = id;
        this.fieldName = fieldName;
        this.address = address;
        this.city = city;
        this.surface = surface;
        this.indoor = indoor;
        this.hourlyPrice = hourlyPrice;
    }

    public Field(String id, String fieldName, String address, String city,
                 SurfaceType surface, Map<DayOfWeek, TimeRange> openingHours,
                 boolean indoor, BigDecimal hourlyPrice, String emailManager) {
        this.id = id;
        this.fieldName = fieldName;
        this.address = address;
        this.city = city;
        this.surface = surface;
        this.openingHours = new EnumMap<>(openingHours);
        this.indoor = indoor;
        this.hourlyPrice = hourlyPrice;
        this.emailManager = emailManager;
    }

    public String getId() {
        return id;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public SurfaceType getSurfaceType() {
        return surface;
    }

    public void setSurfaceType(SurfaceType surface) {
        this.surface = surface;
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

    public Map<DayOfWeek, TimeRange> getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(DayOfWeek day, LocalTime open, LocalTime close) {
        openingHours.put(day, new TimeRange(open, close));
    }

    public void setCity(String city) {
        this.city = city;
    }

    public boolean isIndoor() {
        return indoor;
    }

    public void setIndoor(boolean indoor) {
        this.indoor = indoor;
    }

    public BigDecimal getHourlyPrice() {
        return hourlyPrice;
    }

    public void setHourlyPrice(BigDecimal hourlyPrice) {
        this.hourlyPrice = hourlyPrice;
    }

    public String getManager() {
        return emailManager;
    }
}
