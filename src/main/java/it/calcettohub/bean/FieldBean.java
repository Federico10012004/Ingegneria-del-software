package it.calcettohub.bean;

import it.calcettohub.model.valueobject.TimeRange;
import it.calcettohub.model.SurfaceType;
import it.calcettohub.utils.ValidationUtils;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.Map;

public class FieldBean {
    private String fieldName;
    private String address;
    private String city;
    private SurfaceType surface;
    private Map<DayOfWeek, TimeRange> openingHours;
    private boolean indoor;
    private BigDecimal hourlyPrice;

    public FieldBean() {
        //empty
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        if (ValidationUtils.isNotEmpty(fieldName)) {
            this.fieldName = fieldName;
        } else {
            throw new IllegalArgumentException("Nome campo non inserito.");
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        if (ValidationUtils.isValidAddress(address)) {
            this.address = address;
        } else {
            throw new IllegalArgumentException("Indirizzo non valido. Inserire via e numero civico.");
        }
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        if (ValidationUtils.isNotEmpty(city)) {
            this.city = city;
        } else {
            throw new IllegalArgumentException("Città non inserita.");
        }
    }

    public SurfaceType getSurface() {
        return surface;
    }

    public void setSurface(SurfaceType surface) {
        if (ValidationUtils.isNotNull(surface)) {
            this.surface = surface;
        } else {
            throw new IllegalArgumentException("Tipo di superficie non valida");
        }
    }

    public Map<DayOfWeek, TimeRange> getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(Map<DayOfWeek, TimeRange> openingHours) {
        if (openingHours == null || openingHours.isEmpty()) {
            throw new IllegalArgumentException("Inserisci almeno un giorno di apertura");
        }
        for (var entry : openingHours.entrySet()) {
            TimeRange tr = entry.getValue();
            if (tr == null) {
                throw new IllegalArgumentException("Orari di apertura/chiusura campo non inseriti.");
            }

            LocalTime open = tr.start();
            LocalTime close = tr.end();

            if (open == null || close == null) {
                throw new IllegalArgumentException("Orari di apertura/chiusura mancanti.");
            }

            if (open.equals(close)) {
                throw new IllegalArgumentException("Apertura e chiusura non possono coincidere.");
            }

            if (open.getMinute() != 0 || close.getMinute() != 0) {
                throw new IllegalArgumentException("Gli orari devono essere su minuti 00 (es. 09:00 - 20:00).");
            }
        }

        this.openingHours = new EnumMap<>(openingHours);
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
        if (hourlyPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Il prezzo deve essere maggiore di 0");
        }

        if (hourlyPrice.scale() > 2) {
            throw new IllegalArgumentException("Il prezzo può avere al massimo 2 decimali.");
        }

        this.hourlyPrice = hourlyPrice;
    }
}
