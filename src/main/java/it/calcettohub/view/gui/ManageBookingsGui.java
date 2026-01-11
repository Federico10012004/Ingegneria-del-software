package it.calcettohub.view.gui;

import it.calcettohub.bean.CancelBookingBean;
import it.calcettohub.controller.BookingController;
import it.calcettohub.exceptions.BookingNotCancelableException;
import it.calcettohub.exceptions.PersistenceException;
import it.calcettohub.exceptions.UnexpectedRoleException;
import it.calcettohub.model.Role;
import it.calcettohub.model.valueobject.BookingView;
import it.calcettohub.utils.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class ManageBookingsGui extends BaseFormerGui {
    @FXML Label errorLabel;
    @FXML TextField reasonTextField;
    @FXML TextField searchField;
    @FXML ImageView homeIcon;
    @FXML Button homeButton;
    @FXML VBox bookingsContainer;
    @FXML VBox noBookingPresent;
    @FXML VBox cancelReason;
    @FXML HBox searchBox;
    @FXML StackPane containerPane;
    @FXML StackPane bookingView;
    @FXML ScrollPane scrollPane;

    private List<BookingView> bookings;
    private final BookingController controller = new BookingController();
    private BookingView bookingToCancel;

    @FXML
    private void initialize() {
        enableSessionCheck();

        bindResponsiveLogo(logoGroup, 1000.0);
        containerPane.maxWidthProperty().bind(
                root.widthProperty().multiply(0.5).add(0)
        );

        containerPane.maxHeightProperty().bind(
                root.widthProperty().multiply(0.43).add(0)
        );

        setNodeVisibility(bookingView, true);
        setNodeVisibility(cancelReason, false);

        findBookings();

        setupSearch();
    }

    private void findBookings() {
        try {
            bookings = controller.showBookings();
        } catch (PersistenceException | UnexpectedRoleException e) {
            showError("Errore", e.getMessage());
            return;
        }

        boolean empty = bookings.isEmpty();

        setNodeVisibility(noBookingPresent, empty);
        setNodeVisibility(scrollPane, !empty);

        if (!empty) {
            bookingsView(bookings);
        } else {
            bookingsContainer.getChildren().clear();
        }
    }

    private void bookingsView(List<BookingView> bookings) {
        bookingsContainer.getChildren().clear();

        for (BookingView booking : bookings) {
            Node bookingCard = createBookingCard(booking);
            if (bookingCard != null) {
                bookingsContainer.getChildren().add(bookingCard);
            }
        }
    }

    private Node createBookingCard(BookingView booking) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BookingCard.fxml"));

        try {
            Node bookingCardNode = loader.load();

            BookingCardGui fxmlController = loader.getController();
            fxmlController.setData(booking, () -> openCancelReason(booking));

            return bookingCardNode;
        } catch (IOException _) {
            System.err.println("Errore nel caricamento del file fxml");
            return null;
        }
    }

    private void openCancelReason(BookingView booking) {
        bookingToCancel = booking;

        reasonTextField.clear();

        setNodeVisibility(scrollPane, false);
        setNodeVisibility(searchBox, false);
        setNodeVisibility(cancelReason, true);

        reasonTextField.requestFocus();
    }

    @FXML
    private void refreshUi() {
        bookingToCancel = null;
        reasonTextField.clear();

        setNodeVisibility(cancelReason, false);
        setNodeVisibility(scrollPane, true);
        setNodeVisibility(searchBox, true);
    }

    @FXML
    private void confirmCancelBooking() {
        if (bookingToCancel == null) return;

        boolean confirm = showConfirmation(
                "Disdetta prenotazione",
                "Sei sicuro di voler disdire questa prenotazione?");
        if (!confirm) return;

        CancelBookingBean bean = new CancelBookingBean();

        try {
            validateField(() -> bean.setBookingCode(bookingToCancel.code()));
            validateField(() -> bean.setReason(reasonTextField.getText().trim()));

            controller.cancelBooking(bean);
        } catch (IllegalArgumentException e) {
            setErrorMessage(errorLabel, e.getMessage());
            showError(errorLabel);
        } catch (BookingNotCancelableException | UnexpectedRoleException e) {
            showError("Errore", e.getMessage());
            refreshUi();
            return;
        }

        refreshUi();
        findBookings();

        showInfo("Prenotazione disdetta con successo");
    }

    private void setupSearch() {
        searchField.textProperty().addListener((_, _, newText) -> filterBookings(newText));
    }

    private void filterBookings(String query) {
        if (query == null || query.isBlank()) {
            bookingsView(bookings);
            return;
        }

        String lowerQuery = query.toLowerCase();

        List<BookingView> filtered = bookings.stream()
                .filter(bv -> bv.fieldName().toLowerCase().startsWith(lowerQuery))
                .toList();

        bookingsView(filtered);
    }

    @Override
    public void reset() {
        searchField.clear();

        refreshUi();
        findBookings();
    }

    @FXML
    private void backToHome() {
        Role role = SessionManager.getInstance().getLoggedUser().getRole();

        switch(role) {
            case PLAYER -> switchTo("Home Player");
            case FIELDMANAGER -> switchTo("Home Field Manager");
            default -> throw new UnexpectedRoleException("Ruolo inatteso");
        }
    }
}
