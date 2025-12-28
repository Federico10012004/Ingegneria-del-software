package it.calcettohub.view.cli;

import it.calcettohub.bean.BookingBean;
import it.calcettohub.controller.BookingController;
import it.calcettohub.exceptions.SlotNotAvailableException;
import it.calcettohub.model.valueobject.TimeRange;
import it.calcettohub.utils.PageManager;

import java.time.LocalDate;
import java.util.List;

public class FieldReservationCli extends CliContext {
    private final BookingController controller = new BookingController();

    public void reservation(String fieldId) {
        enableSessionCheck();

        setEscHandler(() -> {
            clearScreen();
            PageManager.pop();
        });

        while (true) {
            try {
                LocalDate date = requestDate("Inserisci la data per cui desideri effettuare la prenotazione (usa formato dd-MM-yyyy): ");

                List<TimeRange> freeSlots = controller.getFreeSlots(fieldId, date);

                TimeRange selectedSlot = selectFreeSlots(freeSlots);

                BookingBean bean = new BookingBean();
                validateBeanField(() -> bean.setFieldId(fieldId));
                validateBeanField(() -> bean.setDate(date));
                validateBeanField(() -> bean.setSlot(selectedSlot));

                controller.fieldBooking(bean);
                print("Prenotazione effettuata con successo.");
                PageManager.pop();
                return;
            } catch (IllegalArgumentException | SlotNotAvailableException e) {
                showExceptionMessage(e);
            }
        }
    }

    private TimeRange selectFreeSlots(List<TimeRange> slots) {
        for (int i = 0; i < slots.size(); i++) {
            TimeRange slot = slots.get(i);
            print((i+1) + ") " + slot.start() + "-" + slot.end());
        }

        while (true) {
            try {
                int choice = requestIntInRange("Seleziona slot per cui desideri prenotare: ", 1, slots.size());

                return slots.get(choice - 1);
            } catch (IllegalArgumentException e) {
                showExceptionMessage(e);
            }
        }
    }
}