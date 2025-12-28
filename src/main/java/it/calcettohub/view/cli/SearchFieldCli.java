package it.calcettohub.view.cli;

import it.calcettohub.bean.SearchFieldBean;
import it.calcettohub.controller.FieldController;
import it.calcettohub.exceptions.EscPressedException;
import it.calcettohub.model.Field;
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

                List<Field> fields = controller.searchField(bean);

                if (fields.isEmpty()) {
                    print("Nessun campo trovato per l'indirizzo e/o città/paese inseriti.");
                } else {
                    selectField(fields);
                }
            } catch (IllegalArgumentException e) {
                showExceptionMessage(e);
            } catch (EscPressedException _) {
                return;
            }
        }
    }

    private void selectField(List<Field> fields) {
        for (int i = 0; i < fields.size(); i++) {
            Field f = fields.get(i);

            print((i+1) + ") " + f.getFieldName());
            print(f.getAddress());
            print(f.getCity());
            print(f.getSurfaceType().toString());
            print(f.isIndoor() ? "Indoor: si" : "Indoor: no");
            print(f.getHourlyPrice().toString());
            print("-----------------------");
        }

        while (true) {
            try {
                int choice = requestIntInRange("Seleziona il campo per cui vuoi effettuare la prenotazione: ", 1, fields.size());

                String fieldId = fields.get(choice-1).getId();

                PageManager.push(() -> new FieldReservationCli().reservation(fieldId));
                return;
            } catch (IllegalArgumentException e) {
                showExceptionMessage(e);
            }
        }
    }
}