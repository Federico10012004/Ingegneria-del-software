package it.calcettohub.view.gui;

import it.calcettohub.bean.BookingBean;
import it.calcettohub.bean.FreeSlotsBean;
import it.calcettohub.controller.BookingController;
import it.calcettohub.exceptions.SlotNotAvailableException;
import it.calcettohub.model.valueobject.TimeRange;
import it.calcettohub.utils.AppContext;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class FieldBookingGui extends BaseFormerGui {
    @FXML private Label errorLabel;
    @FXML private Label slotLabel;
    @FXML private DatePicker dateField;
    @FXML private TilePane slotsPane;
    @FXML private StackPane containerPane;
    @FXML private Button bookingButton;

    private final String fieldId = AppContext.getFieldId();
    private final BookingController controller = new BookingController();
    private final ToggleGroup slotGroup = new ToggleGroup();
    private TimeRange selectedSlot;
    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("HH:mm");

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

        setNodeVisibility(errorLabel, false);
        setNodeVisibility(slotLabel, false);
        setNodeVisibility(bookingButton, false);

        slotGroup.selectedToggleProperty().addListener((_, _, val) -> {
            selectedSlot = (val == null) ? null : (TimeRange) val.getUserData();
            setNodeVisibility(bookingButton, selectedSlot != null);
        });
    }

    @FXML
    private void searchFreeSlots() {
        try {
            FreeSlotsBean bean = new FreeSlotsBean();
            validateField(() -> bean.setFieldId(fieldId));
            validateField(() -> bean.setDate(dateField.getValue()));

            List<TimeRange> freeSlots = controller.getFreeSlots(bean);

            showFreeSlots(freeSlots);
            setNodeVisibility(slotLabel, true);
            setNodeVisibility(errorLabel, false);
        } catch (IllegalArgumentException | SlotNotAvailableException e) {
            setErrorMessage(errorLabel, e.getMessage());
            showErrorLabel(errorLabel);
        }
    }

    private void showFreeSlots(List<TimeRange> slots) {
        slotsPane.getChildren().clear();
        slotGroup.getToggles().clear();
        selectedSlot = null;
        setNodeVisibility(bookingButton, false);

        for (TimeRange slot : slots) {
            String time = TIME_FMT.format(slot.start()) + " - " + TIME_FMT.format(slot.end());

            ToggleButton button = new ToggleButton(time);
            button.getStyleClass().addAll("slot-button");
            button.setToggleGroup(slotGroup);
            button.setUserData(slot);

            slotsPane.getChildren().add(button);
        }
    }

    @FXML
    private void completeBooking() {
        try {
            BookingBean bean = new BookingBean();
            validateField(() -> bean.setFieldId(fieldId));
            validateField(() -> bean.setSlot(selectedSlot.onDate(dateField.getValue())));

            controller.fieldBooking(bean);
            showInfo("Prenotazione effettuata con successo");

            switchTo("Search Field");
        } catch (IllegalArgumentException | SlotNotAvailableException e) {
            setErrorMessage(errorLabel, e.getMessage());
            showErrorLabel(errorLabel);
        }
    }

    @FXML
    private void goBack() {
        switchTo("Search Field");
    }

    @FXML
    private void showDatePicker() {
        dateField.show();
    }

    @Override
    public void reset() {
        slotsPane.getChildren().clear();
        slotGroup.getToggles().clear();
        dateField.setValue(null);
        selectedSlot = null;

        setNodeVisibility(errorLabel, false);
        setNodeVisibility(slotLabel, false);
        setNodeVisibility(bookingButton, false);
    }
}
