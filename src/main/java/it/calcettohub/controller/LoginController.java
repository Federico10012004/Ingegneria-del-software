package it.calcettohub.controller;

import it.calcettohub.bean.LoginBean;
import it.calcettohub.dao.FieldManagerDao;
import it.calcettohub.dao.PlayerDao;
import it.calcettohub.dao.abstractfactory.DaoFactory;
import it.calcettohub.exceptions.EmailNotFoundException;
import it.calcettohub.exceptions.InvalidPasswordException;
import it.calcettohub.model.FieldManager;
import it.calcettohub.model.Player;
import it.calcettohub.model.Role;
import it.calcettohub.model.User;
import it.calcettohub.util.AppContext;
import it.calcettohub.util.PasswordUtils;
import it.calcettohub.util.SessionManager;

import java.util.Optional;

public class LoginController {

    public User login(LoginBean bean) throws EmailNotFoundException, InvalidPasswordException {
        Role role = AppContext.getSelectedRole();
        String email = bean.getEmail();
        String password = bean.getPassword();

        switch (role) {
            case PLAYER -> {
                PlayerDao dao = DaoFactory.getInstance().getPlayerDao();
                Optional<Player> opt = dao.findByEmail(email);

                if (opt.isEmpty()) {
                    throw new EmailNotFoundException();
                } else {
                    Player player = opt.get();
                    boolean validation = PasswordUtils.checkPassword(password, player.getPassword());

                    if (validation) {
                        SessionManager.getInstance().createSession(email);
                        return player;
                    } else {
                        throw new InvalidPasswordException();
                    }
                }
            }

            case FIELDMANAGER -> {
                FieldManagerDao dao = DaoFactory.getInstance().getFieldManagerDao();
                Optional<FieldManager> opt = dao.findByEmail(email);

                if (opt.isEmpty()) {
                    throw new EmailNotFoundException();
                } else {
                    FieldManager fieldManager = opt.get();
                    boolean validation = PasswordUtils.checkPassword(password, fieldManager.getPassword());

                    if (validation) {
                        SessionManager.getInstance().createSession(email);
                        return fieldManager;
                    } else {
                        throw new InvalidPasswordException();
                    }
                }
            }

            default -> {
                return null;
            }
        }
    }
}
