package it.calcettohub.utils;

import it.calcettohub.model.valueobject.DateTimeRange;
import it.calcettohub.model.valueobject.TimeRange;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AvailabilityUtils {
    private static final Duration SLOT = Duration.ofMinutes(60);

    private AvailabilityUtils() {}

    public static LocalDateTime startDateTime(LocalDate date, TimeRange tr) {
        return tr.onDate(date).start();
    }

    private static List<DateTimeRange> buildSlots(LocalDate date, TimeRange openingHours) {
        DateTimeRange oh = openingHours.onDate(date);

        List<DateTimeRange> slots = new ArrayList<>();
        LocalDateTime cursor = oh.start();

        int safetyMax = 48;
        while (!cursor.plus(SLOT).isAfter(oh.end())) {
            LocalDateTime next = cursor.plus(SLOT);
            slots.add(new DateTimeRange(cursor, next));
            cursor = next;

            if (slots.size() > safetyMax) {
                throw new IllegalStateException("Troppi slot generati: openingHours probabilmente non valido.");
            }
        }

        return slots;
    }

    public static List<TimeRange> getAvailableSlots(LocalDate date, TimeRange openingHours, List<TimeRange> busyRanges) {
        List<DateTimeRange> allSlots = buildSlots(date, openingHours);

        List<DateTimeRange> busy = busyRanges.stream()
                .map(tr -> tr.onDate(date))
                .toList();

        List<TimeRange> available = new ArrayList<>();
        for (DateTimeRange slot : allSlots) {
            boolean isBusy = false;

            for (DateTimeRange b : busy) {
                if (slot.overlaps(b)) {
                    isBusy = true;
                    break;
                }
            }
            if (!isBusy) {
                available.add(new TimeRange(slot.start().toLocalTime(), slot.end().toLocalTime()));
            }
        }

        return available;
    }
}
