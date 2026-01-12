package it.calcettohub.view.gui;

import it.calcettohub.bean.FieldBean;
import it.calcettohub.controller.FieldController;
import it.calcettohub.exceptions.PersistenceException;
import it.calcettohub.model.Field;
import it.calcettohub.model.valueobject.TimeRange;
import it.calcettohub.model.SurfaceType;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.EnumMap;
import java.util.List;

public class FieldManagementGui extends BaseFormerGui {
    @FXML Label errorLabel;
    @FXML Label errorLabel1;
    @FXML Label mondayStatusLabel;
    @FXML Label tuesdayStatusLabel;
    @FXML Label wednesdayStatusLabel;
    @FXML Label thursdayStatusLabel;
    @FXML Label fridayStatusLabel;
    @FXML Label saturdayStatusLabel;
    @FXML Label sundayStatusLabel;
    @FXML TextField searchField;
    @FXML TextField fieldName;
    @FXML TextField fieldAddress;
    @FXML TextField fieldCity;
    @FXML TextField fieldSurface;
    @FXML TextField fieldPrice;
    @FXML ImageView homeIcon;
    @FXML Button addFieldButton;
    @FXML Button homeButton;
    @FXML Button confirmButton;
    @FXML Button backButton;
    @FXML Button backButton1;
    @FXML RadioButton indoorYes;
    @FXML RadioButton indoorNo;
    @FXML ToggleButton mondayOpen;
    @FXML ToggleButton tuesdayOpen;
    @FXML ToggleButton wednesdayOpen;
    @FXML ToggleButton thursdayOpen;
    @FXML ToggleButton fridayOpen;
    @FXML ToggleButton saturdayOpen;
    @FXML ToggleButton sundayOpen;
    @FXML Spinner<LocalTime> startMonday;
    @FXML Spinner<LocalTime> endMonday;
    @FXML Spinner<LocalTime> startTuesday;
    @FXML Spinner<LocalTime> endTuesday;
    @FXML Spinner<LocalTime> startWednesday;
    @FXML Spinner<LocalTime> endWednesday;
    @FXML Spinner<LocalTime> startThursday;
    @FXML Spinner<LocalTime> endThursday;
    @FXML Spinner<LocalTime> startFriday;
    @FXML Spinner<LocalTime> endFriday;
    @FXML Spinner<LocalTime> startSaturday;
    @FXML Spinner<LocalTime> endSaturday;
    @FXML Spinner<LocalTime> startSunday;
    @FXML Spinner<LocalTime> endSunday;
    @FXML VBox fieldsContainer;
    @FXML VBox noFieldPresent;
    @FXML StackPane containerPane;
    @FXML StackPane fieldView;
    @FXML StackPane fieldInformation;
    @FXML StackPane fieldAvailability;
    @FXML ScrollPane scrollPane;

    private List<Field> fields;
    private final FieldController controller = new FieldController();
    private FieldBean bean;

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

        setNodeVisibility(fieldView, true);
        setNodeVisibility(fieldInformation, false);
        setNodeVisibility(fieldAvailability, false);
        setNodeVisibility(errorLabel, false);
        setNodeVisibility(errorLabel1, false);

        initTimeSpinner(startMonday, 9);
        initTimeSpinner(endMonday, 10);

        initTimeSpinner(startTuesday, 9);
        initTimeSpinner(endTuesday, 10);

        initTimeSpinner(startWednesday, 9);
        initTimeSpinner(endWednesday, 10);

        initTimeSpinner(startThursday, 9);
        initTimeSpinner(endThursday, 10);

        initTimeSpinner(startFriday, 9);
        initTimeSpinner(endFriday, 10);

        initTimeSpinner(startSaturday, 9);
        initTimeSpinner(endSaturday, 10);

        initTimeSpinner(startSunday, 9);
        initTimeSpinner(endSunday, 10);

        setupDay(mondayOpen, mondayStatusLabel, startMonday, endMonday, true);
        setupDay(tuesdayOpen, tuesdayStatusLabel, startTuesday, endTuesday, true);
        setupDay(wednesdayOpen, wednesdayStatusLabel, startWednesday, endWednesday, false);
        setupDay(thursdayOpen, thursdayStatusLabel, startThursday, endThursday, true);
        setupDay(fridayOpen, fridayStatusLabel, startFriday, endFriday, true);
        setupDay(saturdayOpen, saturdayStatusLabel, startSaturday, endSaturday, true);
        setupDay(sundayOpen, sundayStatusLabel, startSunday, endSunday, true);

        findField();

        setupSearch();
    }

    private void findField() {
        try {
            fields = controller.getFields();
        } catch (PersistenceException e) {
            showError("Errore", e.getMessage());
            return;
        }

        boolean empty = fields.isEmpty();

        setNodeVisibility(noFieldPresent, empty);
        setNodeVisibility(scrollPane, !empty);

        if (!empty) {
            showFields(fields);
        } else {
            fieldsContainer.getChildren().clear();
        }
    }

    private void showFields(List<Field> fields) {
        fieldsContainer.getChildren().clear();

        for (Field field : fields) {
            Node fieldCard = createFieldCard(field);
            if (fieldCard != null) {
                fieldsContainer.getChildren().add(fieldCard);
            }
        }
    }

    private Node createFieldCard(Field field) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FieldCard.fxml"));

        try {
            Node fieldCardNode = loader.load();

            FieldCardGui fxmlController = loader.getController();
            fxmlController.setData(field, () -> deleteField(field));

            return fieldCardNode;
        } catch (IOException _) {
            System.err.println("Errore nel caricamento del file fxml");
            return null;
        }
    }

    private void deleteField(Field field) {
        boolean confirm = showConfirmation(
                "Eliminazione campo",
                "Vuoi davvero eliminare questo campo?");

        if (!confirm) return;

        controller.delete(field.getId());

        findField();

        showInfo("Campo eliminato con successo");
    }

    private void setupSearch() {
        searchField.textProperty().addListener((_, _, newText) -> filterFields(newText));
    }

    private void filterFields(String query) {
        if (query == null || query.isBlank()) {
            showFields(fields);
            return;
        }

        String lowerQuery = query.toLowerCase();

        List<Field> filtered = fields.stream()
                .filter(f -> f.getFieldName().toLowerCase().startsWith(lowerQuery))
                .toList();

        showFields(filtered);
    }

    private void setupDay(ToggleButton openToggle, Label statusLabel,
                          Spinner<?> start, Spinner<?> end, boolean defaultOpen) {

        openToggle.setSelected(defaultOpen);

        statusLabel.textProperty().bind(
                Bindings.when(openToggle.selectedProperty())
                        .then("Aperto")
                        .otherwise("Chiuso")
        );

        start.visibleProperty().bind(openToggle.selectedProperty());
        start.managedProperty().bind(openToggle.selectedProperty());

        end.visibleProperty().bind(openToggle.selectedProperty());
        end.managedProperty().bind(openToggle.selectedProperty());
    }

    @FXML
    private void showAddField() {
        bean = new FieldBean();

        setNodeVisibility(fieldView, false);
        setNodeVisibility(fieldInformation, true);

        fieldName.requestFocus();
    }

    @FXML
    private void showAvailability() {
        try {
            validateField(() -> bean.setFieldName(fieldName.getText().trim()));
            validateField(() -> bean.setAddress(fieldAddress.getText().trim()));
            validateField(() -> bean.setCity(fieldCity.getText().trim()));
            validateField(() -> bean.setSurface(SurfaceType.fromString(fieldSurface.getText().trim())));

            String raw = fieldPrice.getText().trim().replace(",", ".");
            BigDecimal price = new BigDecimal(raw);

            validateField(() -> bean.setHourlyPrice(price));

            if (indoorYes.isSelected() || indoorNo.isSelected()) {
                validateField(() -> bean.setIndoor(indoorYes.isSelected()));
            } else {
                setErrorMessage(errorLabel, "Campo indoor non selezionato.");
                showErrorLabel(errorLabel);
                return;
            }

        } catch (NumberFormatException _) {
            setErrorMessage(errorLabel, "Prezzo orario non valido.");
            showErrorLabel(errorLabel);
            return;
        } catch (IllegalArgumentException e) {
            setErrorMessage(errorLabel, e.getMessage());
            showErrorLabel(errorLabel);
            return;
        }

        setNodeVisibility(fieldInformation, false);
        setNodeVisibility(fieldAvailability, true);
    }

    @FXML
    private void backToFieldView() {
        reset();
    }

    @FXML
    private void backToFieldInformation() {
        setNodeVisibility(fieldAvailability, false);
        setNodeVisibility(errorLabel1, false);
        setNodeVisibility(errorLabel, false);
        setNodeVisibility(fieldInformation, true);
    }

    @FXML
    private void backToHome() {
        switchTo("Home Field Manager");
    }

    private static class LocalTimeSpinnerValueFactory extends SpinnerValueFactory<LocalTime> {

        LocalTimeSpinnerValueFactory(int defaultHour) {
            setConverter(new javafx.util.StringConverter<>() {
                @Override
                public String toString(LocalTime t) {
                    return t == null ? "" : t.toString();
                }

                @Override
                public LocalTime fromString(String s) {
                    return LocalTime.parse(s);
                }
            });

            setValue(LocalTime.of(defaultHour, 0));
        }

        @Override
        public void decrement(int steps) {
            setValue(getValue().minusHours(steps));
        }

        @Override
        public void increment(int steps) {
            setValue(getValue().plusHours(steps));
        }
    }

    private void initTimeSpinner(Spinner<LocalTime> spinner, int defaultHour) {
        spinner.setValueFactory(new LocalTimeSpinnerValueFactory(defaultHour));
        spinner.setEditable(false);
    }

    @FXML
    private void addField() {
        EnumMap<DayOfWeek, TimeRange> openingHours = new EnumMap<>(DayOfWeek.class);

        addDayIfOpen(openingHours, DayOfWeek.MONDAY, mondayOpen, startMonday, endMonday);
        addDayIfOpen(openingHours, DayOfWeek.TUESDAY, tuesdayOpen, startTuesday, endTuesday);
        addDayIfOpen(openingHours, DayOfWeek.WEDNESDAY, wednesdayOpen, startWednesday, endWednesday);
        addDayIfOpen(openingHours, DayOfWeek.THURSDAY, thursdayOpen, startThursday, endThursday);
        addDayIfOpen(openingHours, DayOfWeek.FRIDAY, fridayOpen, startFriday, endFriday);
        addDayIfOpen(openingHours, DayOfWeek.SATURDAY, saturdayOpen, startSaturday, endSaturday);
        addDayIfOpen(openingHours, DayOfWeek.SUNDAY, sundayOpen, startSunday, endSunday);

        try {
            bean.setOpeningHours(openingHours);

            controller.add(bean);

            findField();

            showInfo("Campo aggiunto con successo");
            reset();
        } catch (IllegalArgumentException e) {
            setErrorMessage(errorLabel1, e.getMessage());
            showErrorLabel(errorLabel1);
        }
    }

    private void addDayIfOpen(EnumMap<DayOfWeek, TimeRange> map, DayOfWeek day, ToggleButton openToggle, Spinner<LocalTime> start, Spinner<LocalTime> end) {
        if (!openToggle.isSelected()) return;

        LocalTime open = start.getValue();
        LocalTime close = end.getValue();

        map.put(day, new TimeRange(open, close));
    }

    @Override
    public void reset() {
        fieldName.clear();
        fieldAddress.clear();
        fieldCity.clear();
        fieldSurface.clear();
        fieldPrice.clear();
        searchField.clear();
        indoorYes.setSelected(false);
        indoorNo.setSelected(false);

        setNodeVisibility(fieldInformation, false);
        setNodeVisibility(fieldAvailability, false);
        setNodeVisibility(fieldView, true);
        setNodeVisibility(errorLabel, false);
        setNodeVisibility(errorLabel1, false);
    }
}