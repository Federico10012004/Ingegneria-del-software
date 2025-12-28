package it.calcettohub.dao.abstractfactory;

import it.calcettohub.dao.*;

public class DemoFactory extends DaoFactory {

    public NotificationDao getNotificationDao() {
        return NotificationDemoDao.getInstance();
    }

    public BookingDao getBookingDao() {
        return BookingDemoDao.getInstance();
    }

    public FieldDao getFieldDao() {
        return FieldDemoDao.getInstance();
    }

    public PlayerDao getPlayerDao() {
        return PlayerDemoDao.getInstance();
    }

    public FieldManagerDao getFieldManagerDao() {
        return FieldManagerDemoDao.getInstance();
    }
}
