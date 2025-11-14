package it.calcettohub.dao.abstractfactory;

import it.calcettohub.dao.*;

public class DatabaseFactory extends DaoFactory {

    public FieldDao getFieldDao() {
        return FieldDatabaseDao.getInstance();
    }

    public PlayerDao getPlayerDao() {
        return PlayerDatabaseDao.getInstance();
    }

    public FieldManagerDao getFieldManagerDao() {
        return FieldManagerDatabaseDao.getInstance();
    }
}
