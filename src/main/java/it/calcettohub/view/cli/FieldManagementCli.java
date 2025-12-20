package it.calcettohub.view.cli;

import it.calcettohub.bean.FieldBean;
import it.calcettohub.controller.FieldController;
import it.calcettohub.exceptions.EscPressedException;
import it.calcettohub.exceptions.SessionExpiredException;
import it.calcettohub.model.Field;
import it.calcettohub.model.OpeningTime;
import it.calcettohub.model.SurfaceType;
import it.calcettohub.util.PageManager;

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
    private List<Field> fields;

    public void start() {
        enableSessionCheck();

        setEscHandler(() -> {
            clearScreen();
            PageManager.pop();
        });

        showFields();

        while (true) {
            print("Cosa desideri fare?");
            printEscInfo();
            System.out.println();

            showMenu(
                    "Aggiungi campo",
                    "Elimina campo"
            );

            try {
                int choice = requestIntInRange("Selezione: ", 1, 2);

                if (choice == 1) {
                    addFields();
                } else {
                    if (fields.isEmpty()) {
                        print("Nessun campo presente. Non è possibile effettuare l'operazione di eliminazione.");
                    } else {
                        deleteField();
                    }
                }
            } catch (SessionExpiredException e) {
                showExceptionMessage(e);
                PageManager.pop();
                return;
            } catch (IllegalArgumentException e) {
                showExceptionMessage(e);
            } catch (EscPressedException _) {
                return;
            }
        }
    }

    private void showFields() {
        fields = controller.getFields();

        if (fields.isEmpty()) {
            print("Nessun campo associato. Inserire nuovi campi.");
        } else {
            for (Field f : fields) {
                print(f.getFieldName());
                print(f.getAddress());
                print(f.getCity());
                print(f.getSurfaceType().toString());
                print("-----------------------");
            }
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
                showFields();

                return;
            } catch (NumberFormatException _) {
                showErrorMessage("Prezzo non valido.");
            } catch (IllegalArgumentException e) {
                showExceptionMessage(e);
            }
        }
    }

    private void deleteField() {
        printTitle("Elimina campo");
        showFields();

        int choice = requestIntInRange("Seleziona campo da eliminare: ", 1, fields.size());
        Field selected = fields.get(choice - 1);

        controller.delete(selected.getId());

        clearScreen();
        print("Campo eliminato con successo.");
        showFields();
    }

    private Map<DayOfWeek, OpeningTime> requestOpeningHours() {
        EnumMap<DayOfWeek, OpeningTime> openingHours = new EnumMap<>(DayOfWeek.class);
        DateTimeFormatter time = DateTimeFormatter.ofPattern("HH:mm");

        print("Inserisci gli orari per ogni giorno (formato HH:mm). Premi INVIO per segnare il giorno come CHIUSO.");

        for (DayOfWeek day : DayOfWeek.values()) {
            String dayItalian = day.getDisplayName(TextStyle.FULL, Locale.ITALIAN);
            dayItalian = dayItalian.substring(0, 1).toUpperCase(Locale.ITALIAN) + dayItalian.substring(1);

            OpeningTime openingTime = readOpeningTimeForDay(dayItalian, time);

            if (openingTime != null) {
                openingHours.put(day, openingTime);
            }
        }
        return openingHours;
    }

    private OpeningTime readOpeningTimeForDay(String day, DateTimeFormatter fmt) {
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

            String closeRaw = requestString(day + " - Chiusura (HH:mm): ");

            LocalTime close;
            try {
                close = LocalTime.parse(closeRaw, fmt);
            } catch (DateTimeParseException _) {
                showErrorMessage("Formato non valido. Usa HH:mm (es. 18:30).");
                continue;
            }

            return new OpeningTime(open, close);
        }
    }
}