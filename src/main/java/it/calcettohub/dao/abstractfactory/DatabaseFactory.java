package it.calcettohub.dao.abstractfactory;

import it.calcettohub.dao.*;

public class DatabaseFactory extends DaoFactory {

    public FieldDao createCampoDao() {
        return FieldDatabaseDao.getInstance();
    }

    public PlayerDao createPlayerDao() {
        return PlayerDatabaseDao.getInstance();
    }

    public FieldManagerDao createFieldManagerDao() {
        return FieldManagerDatabaseDao.getInstance();
    }
}
