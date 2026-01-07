package it.calcettohub.utils;

import it.calcettohub.model.valueobject.DateTimeRange;
import it.calcettohub.model.valueobject.TimeRange;

import java.time.LocalDate;

public class NotificationMessages {
    private static final String IN_DATA = " in data ";

    private NotificationMessages() {}

    public static String bookingCreatedMessage(String fieldName, DateTimeRange slot, String playerEmail) {
        return "Nuova prenotazione effettuata da " + playerEmail + " per il campo " + fieldName +
                        IN_DATA + slot.start().toLocalDate() + " alle ore " + slot.start().toLocalTime() +
                        "-" + slot.end().toLocalTime();
    }

    public static String bookingCancelledByPlayerMessage(String fieldName, String playerEmail, LocalDate date, TimeRange slot, String reason) {
        return "Disdetta: " + playerEmail + " ha disdetto la prenotazione per il campo " +
                        fieldName + IN_DATA + date + " e ora " + slot.start() +
                        "-" + slot.end() + ". Motivo: " + reason;
    }

    public static String bookingCancelledByManagerMessage(String fieldName, LocalDate date, TimeRange slot, String reason) {
        return "La tua prenotazione per il campo " + fieldName + IN_DATA + date +
                        " e ora " + slot.start() + "-" + slot.end() +
                        " è stata cancellata dal gestore. Motivo: " + reason;
    }
}
