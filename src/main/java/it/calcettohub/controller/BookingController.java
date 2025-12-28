package it.calcettohub.controller;

import it.calcettohub.bean.BookingBean;
import it.calcettohub.bean.CancelBookingBean;
import it.calcettohub.dao.BookingDao;
import it.calcettohub.dao.FieldDao;
import it.calcettohub.dao.NotificationDao;
import it.calcettohub.dao.abstractfactory.DaoFactory;
import it.calcettohub.exceptions.SlotNotAvailableException;
import it.calcettohub.exceptions.UnexpectedRoleException;
import it.calcettohub.model.*;
import it.calcettohub.model.valueobject.Notification;
import it.calcettohub.model.valueobject.TimeRange;
import it.calcettohub.utils.AvailabilityUtils;
import it.calcettohub.utils.SessionManager;
import it.calcettohub.utils.ValidationUtils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class BookingController {
    private final BookingDao bookingDao = DaoFactory.getInstance().getBookingDao();
    private final FieldDao fieldDao = DaoFactory.getInstance().getFieldDao();
    private final NotificationDao notificationDao = DaoFactory.getInstance().getNotificationDao();

    public List<TimeRange> getFreeSlots(String fieldId, LocalDate date) {
        if (ValidationUtils.isPastBookingDate(date)) {
            throw new IllegalArgumentException("La data di prenotazione non può essere nel passato.");
        }

        Field field = fieldDao.findById(fieldId);

        TimeRange openingHours = field.getOpeningHours().get(date.getDayOfWeek());
        if (openingHours == null) {
            throw new IllegalArgumentException("Campo chiuso per la data scelta.");
        }

        List<TimeRange> busySlots = bookingDao.findByFieldAndDate(field.getId(), date);
        List<TimeRange> freeSlots = AvailabilityUtils.getAvailableSlots(openingHours, busySlots);

        if (date.equals(LocalDate.now())) {
            LocalTime now = LocalTime.now();
            LocalTime minStart = nextSlotStart(openingHours.start(), now);
            freeSlots = freeSlots.stream()
                    .filter(s -> !s.start().isBefore(minStart))
                    .toList();
        }

        if (freeSlots.isEmpty()) {
            throw new SlotNotAvailableException("Nessuno slot disponibile per la data inserita.");
        }

        return freeSlots;
    }

    private LocalTime nextSlotStart(LocalTime openingStart, LocalTime now) {
        if (now.isBefore(openingStart)) return openingStart;

        long minutesFromOpen = Duration.between(openingStart, now).toMinutes();
        long slotMinutes = 60;

        long slotsPassed = (minutesFromOpen + slotMinutes - 1) / slotMinutes;
        return openingStart.plusMinutes(slotsPassed * slotMinutes);
    }

    public void fieldBooking(BookingBean bean) {
        String fieldId = bean.getFieldId();
        LocalDate date = bean.getDate();
        TimeRange slot = bean.getSlot();

        Field field = fieldDao.findById(fieldId);

        if (date.equals(LocalDate.now()) && slot.start().isBefore(LocalTime.now())) {
            throw new SlotNotAvailableException("Slot già iniziato.");
        }

        List<TimeRange> busy = bookingDao.findByFieldAndDate(fieldId, date);
        for (TimeRange b : busy) {
            if (slot.overlaps(b)) {
                throw new SlotNotAvailableException("Slot non disponibile.");
            }
        }

        String playerEmail = SessionManager.getInstance().getLoggedUser().getEmail();
        String managerEmail = field.getManager();

        Booking booking = new Booking(fieldId, playerEmail, date, slot.start(), slot.end());
        bookingDao.add(booking);
        sendBookingNotification(field.getFieldName(), date, slot, playerEmail, managerEmail);
    }

    public void cancelBooking(CancelBookingBean bean) {
        Booking booking = bookingDao.findByCode(bean.getBookingCode());
        Field field = fieldDao.findById(booking.getFieldId());

        User user = SessionManager.getInstance().getLoggedUser();
        TimeRange slot = new TimeRange(booking.getStartTime(), booking.getEndTime());

        switch (user.getRole()) {
            case PLAYER -> {
                if (!booking.getPlayerEmail().equals(user.getEmail())) {
                    throw new IllegalArgumentException("Utente non autorizzato.");
                }

                bookingDao.cancellation(booking.getCode());
                notifyManagerCancelByPlayer(field.getFieldName(), booking.getPlayerEmail(), field.getManager(), booking.getDate(), slot, bean.getReason());
            }
            case FIELDMANAGER -> {
                if (!field.getManager().equals(user.getEmail())) {
                    throw new IllegalArgumentException("Utente non autorizzato.");
                }

                bookingDao.cancellation(booking.getCode());
                notifyPlayerCancelByManager(field.getFieldName(), booking.getPlayerEmail(), booking.getDate(), slot, bean.getReason());
            }
            default -> throw new UnexpectedRoleException("Ruolo non supportato per la cancellazione");
        }
    }

    private void sendBookingNotification(String fieldName, LocalDate date, TimeRange slot, String playerEmail, String managerEmail) {
        notificationDao.add(Notification.unread(managerEmail,
                "Nuova prenotazione effettuata da " + playerEmail + " per il campo " + fieldName +
                        " in data " + date + " alle ore " + slot.start() + "-" + slot.end()));
    }

    private void notifyManagerCancelByPlayer(String fieldName, String playerEmail, String managerEmail, LocalDate date, TimeRange slot, String reason) {
        notificationDao.add(Notification.unread(managerEmail,
                "Disdetta: " + playerEmail + " ha disdetto la prenotazione per il campo " +
                        fieldName + " in data " + date + " e ora " + slot.start() +
                        "-" + slot.end() + ". Motivo: " + reason));
    }

    private void notifyPlayerCancelByManager(String fieldName, String playerEmail, LocalDate date, TimeRange slot, String reason) {
        notificationDao.add(Notification.unread(playerEmail,
                "La tua prenotazione per il campo " + fieldName + " in data " + date +
                        " e ora " + slot.start() + "-" + slot.end() +
                        " è stata cancellata dal gestore. Motivo: " + reason));
    }
}