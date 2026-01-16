package it.calcettohub.controller;

import it.calcettohub.bean.AddFieldBean;
import it.calcettohub.bean.GetFieldBean;
import it.calcettohub.bean.SearchFieldBean;
import it.calcettohub.dao.FieldDao;
import it.calcettohub.dao.abstractfactory.DaoFactory;
import it.calcettohub.model.Field;
import it.calcettohub.model.valueobject.FieldData;
import it.calcettohub.model.valueobject.TimeRange;
import it.calcettohub.model.SurfaceType;
import it.calcettohub.model.User;
import it.calcettohub.utils.SessionManager;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class FieldController {
    private final FieldDao dao = DaoFactory.getInstance().getFieldDao();

    public List<GetFieldBean> getFields() {
        User user = SessionManager.getInstance().getLoggedUser();

        List<GetFieldBean> result = new ArrayList<>();
        List<Field> fields = dao.findFieldsByManager(user.getEmail());
        for (Field f : fields) {
            GetFieldBean bean = toBean(f);
            result.add(bean);
        }

        return result;
    }

    public void delete(String idField) {
        dao.delete(idField);
    }

    public void add(AddFieldBean bean) {
        User user = SessionManager.getInstance().getLoggedUser();

        String fieldName = bean.getFieldName();
        String address = bean.getAddress();
        String city = bean.getCity();
        SurfaceType surfaceType = bean.getSurface();
        boolean indoor = bean.isIndoor();
        BigDecimal price = bean.getHourlyPrice();

        Map<DayOfWeek, TimeRange> openingHours = new EnumMap<>(DayOfWeek.class);
        for (var e : bean.getOpeningHours().entrySet()) {
            openingHours.put(e.getKey(), new TimeRange(e.getValue().getStart(), e.getValue().getEnd()));
        }

        Field field = new Field(new FieldData(fieldName, address, city, surfaceType, openingHours, indoor, price, user.getEmail()));

        dao.add(field);
    }

    public List<GetFieldBean> searchField(SearchFieldBean bean) {
        String address = bean.getAddress();
        String city = bean.getCity();

        List<GetFieldBean> result = new ArrayList<>();
        List<Field> fields = dao.searchFields(address, city);
        for (Field f : fields) {
            GetFieldBean getFieldBean = toBean(f);
            result.add(getFieldBean);
        }

        return result;
    }

    private static GetFieldBean toBean(Field f) {
        return new GetFieldBean(
                f.getId(), f.getFieldName(), f.getAddress(), f.getCity(),
                f.getSurfaceType().toString(), f.isIndoor(), f.getHourlyPrice()
        );
    }
}