package it.calcettohub.model.valueobject;

import it.calcettohub.model.SurfaceType;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.Map;

public record FieldData(
        String fieldName,
        String address,
        String city,
        SurfaceType surface,
        Map<DayOfWeek, TimeRange> openingHours,
        boolean indoor,
        BigDecimal hourlyPrice,
        String emailManager
) {}
