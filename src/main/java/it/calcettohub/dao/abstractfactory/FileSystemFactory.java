package it.calcettohub.dao.abstractfactory;

import it.calcettohub.dao.*;

public class FileSystemFactory extends DaoFactory {

    public FieldDao getFieldDao() {
        return FieldFileSystemDao.getInstance();
    }
    public PlayerDao getPlayerDao() {
        return PlayerFileSystemDao.getInstance();
    }
    public FieldManagerDao getFieldManagerDao() {
        return FieldManagerFileSystemDao.getInstance();
    }
}
