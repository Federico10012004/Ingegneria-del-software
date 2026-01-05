package it.calcettohub.controller;

import it.calcettohub.bean.BookingBean;
import it.calcettohub.bean.CancelBookingBean;
import it.calcettohub.dao.BookingDao;
import it.calcettohub.dao.FieldDao;
import it.calcettohub.dao.NotificationDao;
import it.calcettohub.dao.abstractfactory.DaoFactory;
import it.calcettohub.exceptions.BookingNotCancelableException;
import it.calcettohub.exceptions.SlotNotAvailableException;
import it.calcettohub.exceptions.UnexpectedRoleException;
import it.calcettohub.model.*;
import it.calcettohub.model.valueobject.BookingView;
import it.calcettohub.model.valueobject.DateTimeRange;
import it.calcettohub.model.valueobject.Notification;
import it.calcettohub.model.valueobject.TimeRange;
import it.calcettohub.utils.AvailabilityUtils;
import it.calcettohub.utils.SessionManager;
import it.calcettohub.utils.ValidationUtils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        List<TimeRange> freeSlots = AvailabilityUtils.getAvailableSlots(date, openingHours, busySlots);

        if (date.equals(LocalDate.now())) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime minStart = nextSlotStart(date, openingHours.start(), now);

            freeSlots = freeSlots.stream()
                    .filter(s -> !AvailabilityUtils.startDateTime(date, s).isBefore(minStart))
                    .toList();
        }

        if (freeSlots.isEmpty()) {
            throw new SlotNotAvailableException("Nessuno slot disponibile per la data inserita.");
        }

        return freeSlots;
    }

    private LocalDateTime nextSlotStart(LocalDate date, LocalTime openingStart, LocalDateTime now) {
        LocalDateTime openDT = date.atTime(openingStart);
        if (now.isBefore(openDT)) return openDT;

        long minutesFromOpen = Duration.between(openDT, now).toMinutes();
        long slotMinutes = 60;

        long slotsPassed = (minutesFromOpen + slotMinutes - 1) / slotMinutes;
        return openDT.plusMinutes(slotsPassed * slotMinutes);
    }

    public void fieldBooking(BookingBean bean) {
        String fieldId = bean.getFieldId();
        DateTimeRange slot = bean.getSlot();
        LocalDate date = slot.date();

        Field field = fieldDao.findById(fieldId);

        if (slot.start().isBefore(LocalDateTime.now())) {
            throw new SlotNotAvailableException("Slot già iniziato.");
        }

        List<TimeRange> busy = bookingDao.findByFieldAndDate(fieldId, date);
        for (TimeRange b : busy) {
            if (slot.overlaps(b.onDate(date))) {
                throw new SlotNotAvailableException("Slot non disponibile.");
            }
        }

        String playerEmail = SessionManager.getInstance().getLoggedUser().getEmail();
        String managerEmail = field.getManager();

        Booking booking = new Booking(fieldId, playerEmail, slot);
        bookingDao.add(booking);
        sendBookingNotification(field.getFieldName(), slot, playerEmail, managerEmail);
    }

    public List<BookingView> showBookings() {
        User user = SessionManager.getInstance().getLoggedUser();

        switch (user.getRole()) {
            case PLAYER -> {
                return bookingDao.findPlayerBookings(user.getEmail());
            }
            case FIELDMANAGER -> {
                return bookingDao.findManagerBookings(user.getEmail());
            }
            default -> throw new UnexpectedRoleException("Ruolo inatteso.");
        }
    }

    public void cancelBooking(CancelBookingBean bean) {
        Booking booking = bookingDao.findByCode(bean.getBookingCode());
        if (!booking.isCancelable(LocalDateTime.now())) {
            throw new BookingNotCancelableException("Prenotazione già disdetta o non più disdicibile.");
        }
        Field field = fieldDao.findById(booking.getFieldId());

        User user = SessionManager.getInstance().getLoggedUser();
        TimeRange slot = new TimeRange(booking.getStartTime(), booking.getEndTime());

        switch (user.getRole()) {
            case PLAYER -> {
                bookingDao.cancellation(booking.getCode());
                notifyManagerCancelByPlayer(field.getFieldName(), booking.getPlayerEmail(), field.getManager(), booking.getDate(), slot, bean.getReason());
            }
            case FIELDMANAGER -> {
                bookingDao.cancellation(booking.getCode());
                notifyPlayerCancelByManager(field.getFieldName(), booking.getPlayerEmail(), booking.getDate(), slot, bean.getReason());
            }
            default -> throw new UnexpectedRoleException("Ruolo inatteso.");
        }
    }

    private void sendBookingNotification(String fieldName, DateTimeRange slot, String playerEmail, String managerEmail) {
        notificationDao.add(Notification.unread(managerEmail, Role.FIELDMANAGER,
                "Nuova prenotazione effettuata da " + playerEmail + " per il campo " + fieldName +
                        " in data " + slot.start().toLocalDate() + " alle ore " + slot.start().toLocalTime() +
                        "-" + slot.end().toLocalTime()));
    }

    private void notifyManagerCancelByPlayer(String fieldName, String playerEmail, String managerEmail, LocalDate date, TimeRange slot, String reason) {
        notificationDao.add(Notification.unread(managerEmail, Role.FIELDMANAGER,
                "Disdetta: " + playerEmail + " ha disdetto la prenotazione per il campo " +
                        fieldName + " in data " + date + " e ora " + slot.start() +
                        "-" + slot.end() + ". Motivo: " + reason));
    }

    private void notifyPlayerCancelByManager(String fieldName, String playerEmail, LocalDate date, TimeRange slot, String reason) {
        notificationDao.add(Notification.unread(playerEmail, Role.PLAYER,
                "La tua prenotazione per il campo " + fieldName + " in data " + date +
                        " e ora " + slot.start() + "-" + slot.end() +
                        " è stata cancellata dal gestore. Motivo: " + reason));
    }
}