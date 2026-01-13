package it.calcettohub.view.cli;

import it.calcettohub.bean.CancelBookingBean;
import it.calcettohub.controller.BookingController;
import it.calcettohub.exceptions.BookingNotCancelableException;
import it.calcettohub.exceptions.EscPressedException;
import it.calcettohub.exceptions.SessionExpiredException;
import it.calcettohub.model.valueobject.BookingView;
import it.calcettohub.utils.PageManager;

import java.util.List;

public class ManageBookingsCli extends CliContext {
    private final BookingController controller = new BookingController();
    private List<BookingView> filteredBookings;
    private String fieldFilter = "";
    private static final String MENU_CANCEL_BOOKING = "Disdici prenotazione";
    private static final String MENU_SEARCH_BY_FIELD = "Cerca prenotazione per nome del campo";
    private static final String MENU_CLEAR_FILTER = "Rimuovi filtro";


    public void start() {
        enableSessionCheck();

        setEscHandler(() -> {
            clearScreen();
            PageManager.pop();
        });

        while (true) {
            clearScreen();
            printTitle("Le tue prenotazioni");

            refreshBookings();
            showBookings();

            if (!fieldFilter.isBlank()) {
                print("Filtro attivo: \"" + fieldFilter + "\"");
            }

            print("Cosa desideri fare?");
            printEscInfo();
            System.out.println();

            showActions();

            try {
                int max = fieldFilter.isBlank() ? 2 : 3;
                int choice = requestIntInRange("Selezione: ", 1, max);

                switch (choice) {
                    case 1 -> {
                        if (filteredBookings.isEmpty()) {
                            print("Nessuna prenotazione trovata. Non è possibile effettuare una disdetta.");
                            requestString("Premi INVIO per tornare indietro");
                        } else {
                            cancelBooking();
                        }
                    }
                    case 2 -> searchByFieldName();
                    case 3 -> fieldFilter = "";
                    default -> throw new IllegalStateException("Scelata non valida" + choice);
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

    private void showActions() {
        if (fieldFilter.isBlank()) {
            showMenu(
                    MENU_CANCEL_BOOKING,
                    MENU_SEARCH_BY_FIELD
            );
        } else {
            showMenu(
                    MENU_CANCEL_BOOKING,
                    MENU_SEARCH_BY_FIELD,
                    MENU_CLEAR_FILTER
            );
        }
    }

    private void refreshBookings() {
        List<BookingView> allBookings = controller.showBookings();
        filteredBookings = applyFieldFilter(allBookings, fieldFilter);

        if (allBookings.isEmpty()) {
            print("Nessuna prenotazione associata.");
            return;
        }

        if (filteredBookings.isEmpty()) {
            print("Nessuna prenotazione trovata per il campo inserito.");
        }
    }

    private List<BookingView> applyFieldFilter(List<BookingView> base, String q) {
        if (q == null || q.isBlank()) return base;
        String lower = q.trim().toLowerCase();

        return base.stream()
                .filter(b -> b.fieldName() != null && b.fieldName().toLowerCase().startsWith(lower))
                .toList();
    }

    private void showBookings() {
        showBookings(false);
    }

    private void showNumberedBookings() {
        showBookings(true);
    }

    private void showBookings(boolean numbered) {
        for (int i = 0; i < filteredBookings.size(); i++) {
            BookingView b = filteredBookings.get(i);

            if (numbered) {
                print((i+1) + ")" + b.fieldName());
            } else {
                print(b.fieldName());
            }

            print(b.slot().date().toString());
            print(b.slot().start().toLocalTime().toString());
            print(b.slot().end().toLocalTime().toString());
            print(b.status().toString());
            print("-----------------------");
        }
    }

    private void cancelBooking() {
        printTitle(MENU_CANCEL_BOOKING);
        showNumberedBookings();

        if (filteredBookings.isEmpty()) {
            print("Nessuna prenotazione trovata. Non è possibile effettuare una disdetta.");
            requestString("Premi INVIO per tornare indietro");
        } else {
            while (true) {
                try {
                    int choice = requestIntInRange("Seleziona prenotazione da disdire: ", 1, filteredBookings.size());
                    BookingView selected = filteredBookings.get(choice - 1);

                    CancelBookingBean bean = new CancelBookingBean();
                    validateBeanField(() -> bean.setBookingCode(selected.code()));
                    validateBeanField(() -> bean.setReason(requestString("Inserisci motivo della disdetta: ")));

                    controller.cancelBooking(bean);
                    clearScreen();
                    print("Prenotazione disdetta con successo.");

                    return;
                } catch (IllegalArgumentException | BookingNotCancelableException e) {
                    showExceptionMessage(e);
                }
            }
        }
    }

    private void searchByFieldName() {
        printTitle("Cerca prenotazioni");

        fieldFilter = requestString("Inserisci nome (o prefisso) da cercare: ").trim();
    }
}
