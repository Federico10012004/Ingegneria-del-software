package it.calcettohub.dao.abstractfactory;

import it.calcettohub.dao.*;

public class FileSystemFactory extends DaoFactory {

    public FieldDao createCampoDao() {
        return FieldFileSystemDao.getInstance();
    }
    public PlayerDao createPlayerDao() {
        return PlayerFileSystemDao.getInstance();
    }
    public FieldManagerDao createFieldManagerDao() {
        return FieldManagerFileSystemDao.getInstance();
    }
}
