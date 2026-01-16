package it.calcettohub.controller;

import it.calcettohub.bean.*;
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
import it.calcettohub.utils.NotificationMessages;
import it.calcettohub.utils.SessionManager;
import it.calcettohub.utils.ValidationUtils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class BookingController {
    private final BookingDao bookingDao = DaoFactory.getInstance().getBookingDao();
    private final FieldDao fieldDao = DaoFactory.getInstance().getFieldDao();
    private final NotificationDao notificationDao = DaoFactory.getInstance().getNotificationDao();

    public List<SlotBean> getFreeSlots(FreeSlotsBean bean) {
        String fieldId = bean.getFieldId();
        LocalDate date = bean.getDate();

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

        List<SlotBean> slots = new ArrayList<>();
        for (TimeRange tr : freeSlots) {
            SlotBean slotBean = new SlotBean(tr.start(), tr.end());
            slots.add(slotBean);
        }
        return slots;
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
        LocalDate date = bean.getDate();
        LocalTime start = bean.getStart();
        LocalTime end = bean.getEnd();
        DateTimeRange slot = new DateTimeRange(date.atTime(start), date.atTime(end));

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
        notificationDao.add(Notification.unread(managerEmail, Role.FIELDMANAGER,
                NotificationMessages.bookingCreatedMessage(field.getFieldName(), slot, playerEmail)));
    }

    public List<BookingViewBean> showBookings() {
        User user = SessionManager.getInstance().getLoggedUser();

        List<BookingViewBean> results = new ArrayList<>();

        switch (user.getRole()) {
            case PLAYER -> {
                List<BookingView> playerBookings = bookingDao.findPlayerBookings(user.getEmail());
                for (BookingView bv : playerBookings) {
                    BookingViewBean bvBean = toBean(bv);
                    results.add(bvBean);
                }

                return results;
            }
            case FIELDMANAGER -> {
                List<BookingView> fieldManagerBookings = bookingDao.findManagerBookings(user.getEmail());
                for (BookingView bv : fieldManagerBookings) {
                    BookingViewBean bvBean = toBean(bv);
                    results.add(bvBean);
                }

                return results;
            }
            default -> throw new UnexpectedRoleException("Ruolo inatteso.");
        }
    }

    private BookingViewBean toBean(BookingView bv) {
        return new BookingViewBean(
                bv.code(),
                bv.fieldName(),
                bv.slot().date(),
                bv.slot().start().toLocalTime(),
                bv.slot().end().toLocalTime(),
                bv.status().toString()
        );
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
                notificationDao.add(Notification.unread(field.getManager(), Role.FIELDMANAGER,
                        NotificationMessages.bookingCancelledByPlayerMessage(field.getFieldName(), booking.getPlayerEmail(), booking.getDate(), slot, bean.getReason())));
            }
            case FIELDMANAGER -> {
                bookingDao.cancellation(booking.getCode());
                notificationDao.add(Notification.unread(booking.getPlayerEmail(), Role.PLAYER,
                        NotificationMessages.bookingCancelledByManagerMessage(field.getFieldName(), booking.getDate(), slot, bean.getReason())));
            }
            default -> throw new UnexpectedRoleException("Ruolo inatteso.");
        }
    }
}