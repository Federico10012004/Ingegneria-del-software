package it.calcettohub.utils;

import it.calcettohub.model.valueobject.TimeRange;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AvailabilityUtils {
    private static final Duration SLOT = Duration.ofMinutes(60);

    private AvailabilityUtils() {}

    private static List<TimeRange> buildSlots(TimeRange openingHours) {
        List<TimeRange> slots = new ArrayList<>();
        LocalTime open = openingHours.start();

        while(!open.plus(SLOT).isAfter(openingHours.end())) {
            slots.add(new TimeRange(open, open.plus(SLOT)));
            open = open.plus(SLOT);
        }

        return slots;
    }

    private static List<TimeRange> filterAvailableSlots(List<TimeRange> allSlots, List<TimeRange> busyRange) {
        List<TimeRange> availableSlot = new ArrayList<>();

        for (TimeRange slot : allSlots) {
            boolean busy = false;

            for (TimeRange busySlot : busyRange) {
                if (slot.overlaps(busySlot)) {
                    busy = true;
                    break;
                }
            }

            if (!busy) {
                availableSlot.add(slot);
            }
        }

        return availableSlot;
    }

    public static List<TimeRange> getAvailableSlots(TimeRange openingHours, List<TimeRange> busyRanges) {
        List<TimeRange> allSlots = buildSlots(openingHours);
        return filterAvailableSlots(allSlots, busyRanges);
    }
}
