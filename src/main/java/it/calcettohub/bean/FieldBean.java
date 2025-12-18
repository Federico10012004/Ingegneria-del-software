package it.calcettohub.bean;

import it.calcettohub.model.OpeningTime;
import it.calcettohub.model.SurfaceType;
import it.calcettohub.util.ValidationUtils;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.Map;
import java.util.regex.Pattern;

public class FieldBean {
    private String fieldName;
    private String address;
    private String city;
    private SurfaceType surface;
    private Map<DayOfWeek, OpeningTime> openingHours;
    private boolean indoor;
    private BigDecimal hourlyPrice;

    private static final Pattern ADDRESS_PATTERN = Pattern.compile("^[A-Za-z'. ]+\\s\\d+(/[A-Za-z])?$");

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
        if (ValidationUtils.isNotNull(address) && ADDRESS_PATTERN.matcher(address).matches()) {
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

    public Map<DayOfWeek, OpeningTime> getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(Map<DayOfWeek, OpeningTime> openingHours) {
        if (openingHours == null || openingHours.isEmpty()) {
            throw new IllegalArgumentException("Inserisci almeno un giorno di apertura");
        }
        for (var entry : openingHours.entrySet()) {
            OpeningTime ot = entry.getValue();

            if (ot == null) {
                throw new IllegalArgumentException("Orari di apertura/chiusura campo non inseriti.");
            }

            LocalTime open = ot.getOpen();
            LocalTime close = ot.getClose();

            if (open == null || close == null) {
                throw new IllegalArgumentException("Orari di apertura/chiusura mancanti.");
            }

            if (!close.isAfter(open)) {
                throw new IllegalArgumentException("La chiusura deve essere successiva all'apertura.");
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
