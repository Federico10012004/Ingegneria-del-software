package it.calcettohub.dao.abstractfactory;

import it.calcettohub.dao.*;

public class FileSystemFactory extends DaoFactory {

    public NotificationDao getNotificationDao() {
        return null;
    }

    public BookingDao getBookingDao() {
        return null;
    }

    public FieldDao getFieldDao() {
        return null;
    }

    public PlayerDao getPlayerDao() {
        return PlayerFileSystemDao.getInstance();
    }

    public FieldManagerDao getFieldManagerDao() {
        return FieldManagerFileSystemDao.getInstance();
    }
}
