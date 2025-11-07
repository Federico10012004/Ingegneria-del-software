package it.calcettohub.dao.abstractfactory;

import it.calcettohub.dao.*;

public class DemoFactory extends DaoFactory {

    public FieldDao createCampoDao() {
        return FieldDemoDao.getInstance();
    }

    public PlayerDao createPlayerDao() {
        return PlayerDemoDao.getInstance();
    }

    public FieldManagerDao createFieldManagerDao() {
        return FieldManagerDemoDao.getInstance();
    }
}
