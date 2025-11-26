package it.calcettohub.controller;

import it.calcettohub.bean.RegisterFieldManagerBean;
import it.calcettohub.bean.RegisterPlayerBean;
import it.calcettohub.dao.FieldManagerDao;
import it.calcettohub.dao.PlayerDao;
import it.calcettohub.dao.abstractfactory.DaoFactory;
import it.calcettohub.exceptions.EmailAlreadyExistsException;
import it.calcettohub.model.FieldManager;
import it.calcettohub.model.Player;
import it.calcettohub.model.PlayerPosition;
import it.calcettohub.util.PasswordUtils;

import java.time.LocalDate;
import java.util.Optional;

public class RegistrationController {

    public void registerPlayer(RegisterPlayerBean bean) throws EmailAlreadyExistsException {
        String name = bean.getName();
        String surname = bean.getSurname();
        LocalDate dateOfBirth = bean.getDateOfBirth();
        String email = bean.getEmail();
        String password = bean.getPassword();
        PlayerPosition position = bean.getPreferredPosition();

        PlayerDao dao = DaoFactory.getInstance().getPlayerDao();
        Optional<Player> opt = dao.findByEmail(email);

        if (opt.isEmpty()) {
            String hashedPassword = PasswordUtils.hashPassword(password);
            Player player = new Player(email, hashedPassword, name, surname, dateOfBirth, position);
            dao.add(player);
        } else {
            throw new EmailAlreadyExistsException();
        }
    }

    public void registerFieldManager(RegisterFieldManagerBean bean) throws EmailAlreadyExistsException {
        String name = bean.getName();
        String surname = bean.getSurname();
        LocalDate dateOfBirth = bean.getDateOfBirth();
        String email = bean.getEmail();
        String password = bean.getPassword();
        String vatNumber = bean.getVatNumber();
        String phoneNumber = bean.getPhoneNumber();

        FieldManagerDao dao = DaoFactory.getInstance().getFieldManagerDao();
        Optional<FieldManager> opt = dao.findByEmail(email);

        if (opt.isEmpty()) {
            String hashedPassword = PasswordUtils.hashPassword(password);
            FieldManager fieldManager = new FieldManager(email, hashedPassword, name, surname, dateOfBirth, vatNumber, phoneNumber);
            dao.add(fieldManager);
        } else {
            throw new EmailAlreadyExistsException();
        }
    }
}
