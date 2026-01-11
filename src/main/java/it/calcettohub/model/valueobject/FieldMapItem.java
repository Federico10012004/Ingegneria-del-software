package it.calcettohub.model.valueobject;

public record FieldMapItem(
        String id,
        String name,
        String address,
        String city,
        Double lat,
        Double lon
) {}
