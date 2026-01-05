package it.calcettohub.model.valueobject;

import it.calcettohub.model.BookingStatus;

public record BookingView (
        String code,
        String fieldName,
        DateTimeRange slot,
        BookingStatus status
) {}