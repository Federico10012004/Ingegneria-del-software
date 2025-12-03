package it.calcettohub.controller;

import it.calcettohub.bean.AccountBean;
import it.calcettohub.bean.FieldManagerAccountBean;
import it.calcettohub.bean.PlayerAccountBean;
import it.calcettohub.dao.FieldManagerDao;
import it.calcettohub.dao.PlayerDao;
import it.calcettohub.dao.abstractfactory.DaoFactory;
import it.calcettohub.model.FieldManager;
import it.calcettohub.model.Player;
import it.calcettohub.model.User;
import it.calcettohub.util.PasswordUtils;
import it.calcettohub.util.SessionManager;

public class AccountController {

    public void updateUser(AccountBean bean) {
        User user = SessionManager.getInstance().getLoggedUser();

        if (bean.getName() != null) user.setName(bean.getName());
        if (bean.getSurname() != null) user.setSurname(bean.getSurname());
        if (bean.getDateOfBirth() != null) user.setDateOfBirth(bean.getDateOfBirth());
        if (bean.getPassword() != null) user.setPassword(PasswordUtils.hashPassword(bean.getPassword()));
        ;

        if (user instanceof Player p && bean instanceof PlayerAccountBean pb) {
            if (pb.getPosition() != null) p.setPreferredPosition(pb.getPosition());
            PlayerDao playerDao = DaoFactory.getInstance().getPlayerDao();
            playerDao.update(p);
            return;
        }

        if (user instanceof FieldManager f && bean instanceof FieldManagerAccountBean fb) {
            if (fb.getPhoneNumber() != null) f.setPhoneNumber(fb.getPhoneNumber());
            FieldManagerDao fieldManagerDao = DaoFactory.getInstance().getFieldManagerDao();
            fieldManagerDao.update(f);
        }
    }
}
