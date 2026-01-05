package it.calcettohub.view.cli;

import it.calcettohub.controller.NotificationController;
import it.calcettohub.exceptions.EscPressedException;
import it.calcettohub.exceptions.SessionExpiredException;
import it.calcettohub.model.valueobject.Notification;
import it.calcettohub.utils.PageManager;

import java.util.List;

public abstract class AbstractHomePageCli extends CliContext {
    private final NotificationController controller = new NotificationController();

    protected abstract String getHomeTitle();

    protected abstract String[] getSpecificOption();

    protected abstract void onSpecificOption(int choice);

    public void start() {
        enableSessionCheck();

        while (true) {
            printTitle(getHomeTitle());
            print("Benvenuto, cosa desideri fare?");

            String[] specificOptions = getSpecificOption();
            int n = specificOptions.length;

            String[] menu = new String[n + 4];
            System.arraycopy(specificOptions, 0, menu, 0, n);
            menu[n] = "Gestisci prenotazioni";
            menu[n + 1] = "Gestisci il tuo account";
            menu[n + 2] = "Notifiche";
            menu[n + 3] = "Esci";

            showMenu(menu);

            try {
                int choice = requestIntInRange("Selezione: ", 1, menu.length);
                clearScreen();

                if (choice <= n) {
                    onSpecificOption(choice);
                } else {
                    switch (choice - n) {
                        case 1 -> PageManager.push(() -> new ManageBookingsCli().start());
                        case 2 -> PageManager.push(() -> new AccountCli().start());
                        case 3 -> showNotifications();
                        case 4 -> System.exit(0);
                        default -> throw new IllegalStateException("Scelta non valida: " + choice);
                    }
                }
            } catch (EscPressedException _) {
                showErrorMessage("Esc non disponibile in questa pagina.");
            } catch (SessionExpiredException e) {
                showExceptionMessage(e);
                PageManager.pop();
                return;
            } catch (IllegalArgumentException e) {
                showExceptionMessage(e);
            }
        }
    }

    private void showNotifications() {
        printTitle("Notifiche");

        List<Notification> notifications = controller.getNotifications();
        for (Notification not : notifications) {
            print(not.message());
            System.out.println();
        }

        if (notifications.isEmpty()) {
            print("Nessuna nuova notifica.");
        } else {
            controller.markAsAlreadyRead();
        }

        requestString("Premi INVIO per tornare alla Home");
    }
}