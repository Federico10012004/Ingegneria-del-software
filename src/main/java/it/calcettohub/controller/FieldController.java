package it.calcettohub.controller;

import it.calcettohub.bean.FieldBean;
import it.calcettohub.dao.FieldDao;
import it.calcettohub.dao.abstractfactory.DaoFactory;
import it.calcettohub.model.Field;
import it.calcettohub.model.OpeningTime;
import it.calcettohub.model.SurfaceType;
import it.calcettohub.model.User;
import it.calcettohub.util.SessionManager;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

public class FieldController {

    private final FieldDao dao = DaoFactory.getInstance().getFieldDao();

    public List<Field> getFields() {
        User user = SessionManager.getInstance().getLoggedUser();

        return dao.findFields(user.getEmail());
    }

    public void delete(String idField) {
        dao.delete(idField);
    }

    public void add(FieldBean bean) {
        User user = SessionManager.getInstance().getLoggedUser();

        String fieldName = bean.getFieldName();
        String address = bean.getAddress();
        String city = bean.getCity();
        SurfaceType surfaceType = bean.getSurface();
        Map<DayOfWeek, OpeningTime> openingHours = bean.getOpeningHours();
        boolean indoor = bean.isIndoor();
        BigDecimal price = bean.getHourlyPrice();

        Field field = new Field(fieldName, address, city, surfaceType, openingHours, indoor, price, user.getEmail());

        dao.add(field);
    }
}
