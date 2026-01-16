package it.calcettohub.view.cli;

import it.calcettohub.bean.GetFieldBean;
import it.calcettohub.bean.SearchFieldBean;
import it.calcettohub.controller.FieldController;
import it.calcettohub.exceptions.EscPressedException;
import it.calcettohub.exceptions.SessionExpiredException;
import it.calcettohub.utils.PageManager;

import java.util.List;

public class SearchFieldCli extends CliContext {
    private final FieldController controller = new FieldController();

    public void search() {
        enableSessionCheck();

        setEscHandler(() -> {
            clearScreen();
            PageManager.pop();
        });

        while (true) {
            printTitle("Cerca campo");
            printEscInfo();

            print("Inserisci indirizzo campo e/o città/paese dove si desidera cercare un campo (inserisci obbligatoriamente almeno uno dei due parametri)");

            SearchFieldBean bean = new SearchFieldBean();

            try {
                validateBeanField(() -> bean.setAddress(requestString("Indirizzo campo (premi INVIO per lasciare vuoto): ")));
                validateBeanField(() -> bean.setCity(requestString("Città/Paese (premi INVIO per lascaire vuoto): ")));
                bean.validate();

                List<GetFieldBean> fields = controller.searchField(bean);

                if (fields.isEmpty()) {
                    print("Nessun campo trovato per l'indirizzo e/o città/paese inseriti.");
                } else {
                    selectField(fields);
                }
            } catch (IllegalArgumentException e) {
                showExceptionMessage(e);
            } catch (EscPressedException _) {
                return;
            } catch (SessionExpiredException e) {
                showExceptionMessage(e);
                expiredSession();
                return;
            }
        }
    }

    private void selectField(List<GetFieldBean> fields) {
        for (int i = 0; i < fields.size(); i++) {
            GetFieldBean fb = fields.get(i);

            print((i+1) + ") " + fb.getFieldName());
            print(fb.getAddress());
            print(fb.getCity());
            print(fb.getSurface());
            print(fb.isIndoor() ? "Indoor: si" : "Indoor: no");
            print(fb.getHourlyPrice().toString());
            print("-----------------------");
        }

        while (true) {
            try {
                int choice = requestIntInRange("Seleziona il campo per cui vuoi effettuare la prenotazione: ", 1, fields.size());

                String fieldId = fields.get(choice-1).getFieldId();

                PageManager.push(() -> new FieldBookingCli().reservation(fieldId));
                return;
            } catch (IllegalArgumentException e) {
                showExceptionMessage(e);
            }
        }
    }
}