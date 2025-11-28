package it.calcettohub.controller;

import it.calcettohub.bean.LoginBean;
import it.calcettohub.dao.FieldManagerDao;
import it.calcettohub.dao.PlayerDao;
import it.calcettohub.dao.abstractfactory.DaoFactory;
import it.calcettohub.exceptions.EmailNotFoundException;
import it.calcettohub.exceptions.InvalidPasswordException;
import it.calcettohub.exceptions.UnexpectedRoleException;
import it.calcettohub.model.FieldManager;
import it.calcettohub.model.Player;
import it.calcettohub.model.Role;
import it.calcettohub.util.PasswordUtils;
import it.calcettohub.util.SessionManager;

import java.util.Optional;

public class LoginController {

    public void login(LoginBean bean) throws EmailNotFoundException, InvalidPasswordException {
        Role role = bean.getRole();
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
                        SessionManager.getInstance().createSession(player);
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
                        SessionManager.getInstance().createSession(fieldManager);
                    } else {
                        throw new InvalidPasswordException();
                    }
                }
            }

            default -> throw new UnexpectedRoleException("Ruolo inatteso durante il login: " + role);
        }
    }
}
