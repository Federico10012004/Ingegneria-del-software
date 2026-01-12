package it.calcettohub.view.cli;

import it.calcettohub.bean.FieldBean;
import it.calcettohub.controller.FieldController;
import it.calcettohub.exceptions.EscPressedException;
import it.calcettohub.exceptions.SessionExpiredException;
import it.calcettohub.model.Field;
import it.calcettohub.model.valueobject.TimeRange;
import it.calcettohub.model.SurfaceType;
import it.calcettohub.utils.PageManager;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FieldManagementCli extends CliContext {
    private final FieldController controller = new FieldController();
    private List<Field> filteredFields;
    private String nameFilter = "";

    public void start() {
        enableSessionCheck();

        setEscHandler(() -> {
            clearScreen();
            PageManager.pop();
        });

        while (true) {
            clearScreen();
            printTitle("Gestione campi");

            refreshFields();
            showFields();

            if (!nameFilter.isBlank()) {
                print("Filtro attivo: \"" + nameFilter + "\"");
            }

            print("Cosa desideri fare?");
            printEscInfo();
            System.out.println();

            showAction();

            try {
                int max = nameFilter.isBlank() ? 3 : 4;
                int choice = requestIntInRange("Selezione: ", 1, max);

                switch (choice) {
                    case 1 -> addFields();
                    case 2 -> deleteField();
                    case 3 -> searchByName();
                    case 4 -> nameFilter = "";
                    default -> throw new IllegalStateException("Scelta non valida" + choice);
                }
            } catch (SessionExpiredException e) {
                showExceptionMessage(e);
                expiredSession();
                return;
            } catch (IllegalArgumentException e) {
                showExceptionMessage(e);
            } catch (EscPressedException _) {
                return;
            }
        }
    }

    private void showAction() {
        if (nameFilter.isBlank()) {
            showMenu(
                    "Aggiungi campo",
                    "Elimina campo",
                    "Cerca campo per nome"
            );
        } else {
            showMenu(
                    "Aggiungi campo",
                    "Elimina campo",
                    "Cerca campo per nome",
                    "Rimuovi filtro"
            );
        }
    }

    private void refreshFields() {
        List<Field> allFields = controller.getFields();
        filteredFields = applyNameFilter(allFields, nameFilter);

        if (allFields.isEmpty()) {
            print("Nessun campo associato. Inserire nuovi campi.");
            return;
        }

        if (filteredFields.isEmpty()) {
            print("Nessun campo trovato con il nome inserito");
        }
    }

    private List<Field> applyNameFilter(List<Field> base, String query) {
        if (query == null || query.isBlank()) return base;

        String lower = query.trim().toLowerCase();
        return base.stream()
                .filter(f -> f.getFieldName() != null &&
                        f.getFieldName().toLowerCase().startsWith(lower))
                .toList();
    }

    private void showFields() {
        showFields(false);
    }

    private void showFieldsNumbered() {
        showFields(true);
    }

    private void showFields(boolean numbered) {
        for (int i = 0; i < filteredFields.size(); i++) {
            Field f = filteredFields.get(i);

            if (numbered) {
                print((i+1) + ") " + f.getFieldName());
            } else {
                print(f.getFieldName());
            }

            print(f.getAddress());
            print(f.getCity());
            print(f.getSurfaceType().toString());
            print(f.isIndoor() ? "Indoor: si" : "Indoor: no");
            print(f.getHourlyPrice().toString());
            print("-----------------------");
        }
    }

    private void addFields() {
        FieldBean bean = new FieldBean();

        while (true) {
            printTitle("Inserisci dati campo");

            try {
                validateBeanField(() -> bean.setFieldName(requestString("Nome del campo: ")));
                validateBeanField(() -> bean.setAddress(requestString("Indirizzo campo: ")));
                validateBeanField(() -> bean.setCity(requestString("Città/Paese: ")));
                validateBeanField(() -> bean.setSurface(SurfaceType.fromString(requestString("Tipo di superficie: "))));
                validateBeanField(() -> bean.setIndoor(requestBoolean("Indoor (s/n): ")));

                String raw = requestString("Inserisci prezzo orario (massimo due cifre decimali): ");
                raw = raw.trim().replace(",", ".");
                BigDecimal price = new BigDecimal(raw);

                validateBeanField(() -> bean.setHourlyPrice(price));
                validateBeanField(() -> bean.setOpeningHours(requestOpeningHours()));

                controller.add(bean);

                clearScreen();
                print("Campo aggiunto con successo.");

                return;
            } catch (NumberFormatException _) {
                showErrorMessage("Prezzo non valido.");
            } catch (IllegalArgumentException e) {
                showExceptionMessage(e);
            }
        }
    }

    private void deleteField() {
        if (filteredFields.isEmpty()) {
            print("Nessun campo presente. Non è possibile effettuare l'operazione di eliminazione.");
            requestString("Premi INVIO per tornare indietro");
        } else {
            printTitle("Eliminazione campo");
            showFieldsNumbered();

            while (true) {
                try {
                    int choice = requestIntInRange("Seleziona campo da eliminare: ", 1, filteredFields.size());
                    Field selected = filteredFields.get(choice - 1);

                    controller.delete(selected.getId());

                    clearScreen();
                    print("Campo eliminato con successo.");

                    return;
                } catch (IllegalArgumentException e) {
                    showExceptionMessage(e);
                }
            }
        }
    }

    private void searchByName() {
        printTitle("Cerca campo");

        nameFilter = requestString("Inserisci nome (o prefisso) da cercare: ").trim();
    }

    private Map<DayOfWeek, TimeRange> requestOpeningHours() {
        EnumMap<DayOfWeek, TimeRange> openingHours = new EnumMap<>(DayOfWeek.class);
        DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm");

        print("Inserisci gli orari per ogni giorno (formato HH:mm). Premi INVIO per segnare il giorno come CHIUSO.");

        for (DayOfWeek day : DayOfWeek.values()) {
            String dayItalian = day.getDisplayName(TextStyle.FULL, Locale.ITALIAN);
            dayItalian = dayItalian.substring(0, 1).toUpperCase(Locale.ITALIAN) + dayItalian.substring(1);

            TimeRange openingTime = readOpeningTimeForDay(dayItalian, time);

            if (openingTime != null) {
                openingHours.put(day, openingTime);
            }
        }
        return openingHours;
    }

    private TimeRange readOpeningTimeForDay(String day, DateTimeFormatter fmt) {
        while (true) {
            String openRaw = requestString(day + " - Apertura (HH:mm, invio=chiuso): ");

            if (openRaw.isEmpty()) {
                return null;
            }

            LocalTime open;
            try {
                open = LocalTime.parse(openRaw, fmt);
            } catch (DateTimeParseException _) {
                showErrorMessage("Formato orario non valido. Usa HH:mm (es. 09:00).");
                continue;
            }

            while (true) {
                String closeRaw = requestString(day + " - Chiusura (HH:mm): ").trim();
                try {
                    LocalTime close = LocalTime.parse(closeRaw, fmt);
                    return new TimeRange(open, close);
                } catch (DateTimeParseException _) {
                    showErrorMessage("Formato chiusura non valido. Usa HH:mm (es. 18:30).");
                }
            }
        }
    }
}