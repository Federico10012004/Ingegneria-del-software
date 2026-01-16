package it.calcettohub.view.cli;

import it.calcettohub.bean.BookingBean;
import it.calcettohub.bean.FreeSlotsBean;
import it.calcettohub.bean.SlotBean;
import it.calcettohub.controller.BookingController;
import it.calcettohub.exceptions.SessionExpiredException;
import it.calcettohub.exceptions.SlotNotAvailableException;
import it.calcettohub.utils.PageManager;

import java.time.LocalDate;
import java.util.List;

public class FieldBookingCli extends CliContext {
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

                FreeSlotsBean bean = new FreeSlotsBean();
                validateBeanField(() -> bean.setFieldId(fieldId));
                validateBeanField(() -> bean.setDate(date));

                List<SlotBean> freeSlots = controller.getFreeSlots(bean);

                SlotBean selectedSlot = selectFreeSlots(freeSlots);


                BookingBean bookingBean = new BookingBean();
                validateBeanField(() -> bookingBean.setFieldId(fieldId));
                validateBeanField(() -> bookingBean.setDate(date));
                validateBeanField(() -> bookingBean.setStart(selectedSlot.getStart()));
                validateBeanField(() -> bookingBean.setEnd(selectedSlot.getEnd()));

                controller.fieldBooking(bookingBean);
                print("Prenotazione effettuata con successo.");
                PageManager.pop();
                return;
            } catch (IllegalArgumentException | SlotNotAvailableException e) {
                showExceptionMessage(e);
            } catch (SessionExpiredException e) {
                showExceptionMessage(e);
                expiredSession();
                return;
            }
        }
    }

    private SlotBean selectFreeSlots(List<SlotBean> slots) {
        for (int i = 0; i < slots.size(); i++) {
            SlotBean slot = slots.get(i);
            print((i+1) + ") " + slot.getStart() + "-" + slot.getEnd());
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