package it.calcettohub.view.gui;

import com.gluonhq.maps.MapPoint;
import com.gluonhq.maps.MapView;
import it.calcettohub.bean.SearchFieldBean;
import it.calcettohub.controller.FieldController;
import it.calcettohub.model.Field;
import it.calcettohub.model.valueobject.FieldMapItem;
import it.calcettohub.utils.AppContext;
import it.calcettohub.utils.GeocodingClient;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchFieldGui extends BaseFormerGui {
    @FXML private Label errorLabel;
    @FXML private TextField addressField;
    @FXML private TextField cityField;
    @FXML private Button searchButton;
    @FXML private Button goForwardButton;
    @FXML private ProgressIndicator loading;
    @FXML private ScrollPane scrollPane;
    @FXML private StackPane mapContainer;
    @FXML private StackPane containerPane;
    @FXML private VBox fieldsList;

    private final FieldController fieldController = new FieldController();
    private final GeocodingClient geocodingClient = new GeocodingClient();
    private final ObjectProperty<String> selectedFieldId = new SimpleObjectProperty<>(null);

    private final Map<String, FieldSelectionCardGui> cardCtrlById = new HashMap<>();

    private volatile boolean searching = false;
    private MapView mapView;
    private FieldsLayer fieldsLayer;

    @FXML
    private void initialize() {
        enableSessionCheck();

        mapView = new MapView();
        fieldsLayer = new FieldsLayer(selectedFieldId::set);
        mapView.addLayer(fieldsLayer);
        mapView.setZoom(6);

        mapView.addEventFilter(MouseEvent.MOUSE_DRAGGED, _ -> fieldsLayer.refresh());
        mapView.addEventFilter(MouseEvent.MOUSE_RELEASED, _ -> fieldsLayer.refresh());
        mapView.addEventFilter(ScrollEvent.SCROLL, _ -> fieldsLayer.refresh());

        mapView.setCenter(new MapPoint(41.9028, 12.4964));

        mapContainer.getChildren().add(mapView);

        selectedFieldId.addListener((_, oldId, newId) -> {

                    if (oldId != null) {
                        var oldCtrl = cardCtrlById.get(oldId);
                        if (oldCtrl != null) oldCtrl.setSelected(false);

                        setMarkerHighlighted(oldId, false);
                    }

                    if (newId != null) {
                        var newCtrl = cardCtrlById.get(newId);
                        if (newCtrl != null) {
                            newCtrl.setSelected(true);
                            scrollToCard(newCtrl.getRoot());
                        }

                        setMarkerHighlighted(newId, true);
                        FieldMapItem it = fieldsLayer.getItem(newId);
                        if (it != null && it.lat() != null && it.lon() != null) {
                            mapView.setCenter(new MapPoint(it.lat(), it.lon()));
                        }
                    }
        });

        bindResponsiveLogo(logoGroup, 1000.0);
        containerPane.maxWidthProperty().bind(
                root.widthProperty().multiply(0.5).add(0)
        );

        containerPane.maxHeightProperty().bind(
                root.widthProperty().multiply(0.43).add(0)
        );

        setNodeVisibility(errorLabel, false);
    }

    @FXML
    private void onSearch() {
        if (searching) return;

        setLoading(true);

        SearchFieldBean bean = new SearchFieldBean();

        try {
            validateField(() -> bean.setAddress(addressField.getText().trim()));
            validateField(() -> bean.setCity(cityField.getText().trim()));
            bean.validate();
        } catch (IllegalArgumentException e) {
            setErrorMessage(errorLabel, e.getMessage());
            showErrorLabel(errorLabel);
            setLoading(false);
            return;
        }

        setNodeVisibility(errorLabel, false);
        Task<List<Field>> task = getTask(bean);
        new Thread(task, "search-fields").start();
    }

    private void setLoading(boolean on) {
        searching = on;
        searchButton.setDisable(on);
        addressField.setDisable(on);
        cityField.setDisable(on);
        goForwardButton.setDisable(on);
        loading.setVisible(on);
        loading.setManaged(on);
    }

    private Task<List<Field>> getTask(SearchFieldBean bean) {
        Task<List<Field>> task = new Task<>() {
            @Override protected List<Field> call() {
                return fieldController.searchField(bean);
            }
        };

        task.setOnSucceeded(_ -> {
            List<Field> fields = task.getValue();
            renderCards(fields);

            if (fields.isEmpty()) {
                clearMapMarkers();
                setLoading(false);
                showInfo("Nessun campo trovato.");
                return;
            }

            Task<List<FieldMapItem>> geoTask = getGeocodeTask(fields);
            new Thread(geoTask, "geocode-fields").start();
        });

        task.setOnFailed(_ -> {
            showError("Errore", task.getException().getMessage());
            setLoading(false);
        });

        return task;
    }

    private Task<List<FieldMapItem>> getGeocodeTask(List<Field> fields) {
        return new Task<>() {
            @Override protected List<FieldMapItem> call() {
                return fields.stream()
                        .map(SearchFieldGui.this::toMapItem)   // geocoding qui
                        .filter(mi -> mi.lat() != null && mi.lon() != null)
                        .toList();
            }

            @Override protected void succeeded() {
                List<FieldMapItem> items = getValue();
                fieldsLayer.setItems(items);

                int total = fields.size();
                int mapped = items.size();

                if (total > 0 && mapped == 0) {
                    clearMapMarkers();

                    mapView.setCenter(new MapPoint(41.9028, 12.4964));
                    mapView.setZoom(6);

                    showInfo("Nessun campo è stato localizzato sulla mappa: gli indirizzi potrebbero essere errati/incompleti (oppure il servizio di geocoding non è disponibile).");
                    setLoading(false);
                    return;
                }

                if (mapped < total) {
                    showInfo((total - mapped) + " campi non sono stati mostrati sulla mappa (indirizzo errato/incompleto).");
                }

                if (!items.isEmpty()) {
                    FieldMapItem first = items.getFirst();
                    mapView.setCenter(new MapPoint(first.lat(), first.lon()));
                    mapView.setZoom(13);
                }

                setLoading(false);
            }

            @Override protected void failed() {
                showError("Errore geocoding", getException().getMessage());
                clearMapMarkers();
                setLoading(false);
            }
        };
    }

    private FieldMapItem toMapItem(Field f) {
        String query = buildQuery(f);

        var opt = geocodingClient.geocode(query);
        if (opt.isEmpty()) {
            return new FieldMapItem(f.getId(), f.getFieldName(), f.getAddress(), f.getCity(), null, null);
        }

        double[] latlon = opt.get();
        return new FieldMapItem(f.getId(), f.getFieldName(), f.getAddress(), f.getCity(), latlon[0], latlon[1]);
    }

    private String buildQuery(Field f) {
        String a = f.getAddress() == null ? "" : f.getAddress().trim();
        String c = f.getCity() == null ? "" : f.getCity().trim();

        String q = (a + ", " + c).trim();
        if (q.startsWith(",")) q = q.substring(1).trim();
        if (q.endsWith(",")) q = q.substring(0, q.length() - 1).trim();
        return q;
    }

    private void renderCards(List<Field> fields) {
        fieldsList.getChildren().clear();
        cardCtrlById.clear();
        selectedFieldId.set(null);

        for (Field field : fields) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FieldSelectionCard.fxml"));

            try {
                Node fieldCard = loader.load();

                FieldSelectionCardGui cardController = loader.getController();
                cardController.setData(field, () -> selectedFieldId.set(field.getId()));

                fieldsList.getChildren().add(fieldCard);
                cardCtrlById.put(field.getId(), cardController);
            } catch (IOException _) {
                System.err.println("Errore nel caricamento del file fxml");
                return;
            }
        }
    }

    private void setMarkerHighlighted(String fieldId, boolean on) {
        Node n = fieldsLayer.getMarker(fieldId);
        if (n == null) return;

        if (n instanceof Circle c) {
            c.setRadius(on ? 11 : 7);
            c.setStrokeWidth(on ? 3 : 1);
        } else {
            if (on) n.getStyleClass().add("marker-selected");
            else n.getStyleClass().remove("marker-selected");
        }

        fieldsLayer.requestLayout();
    }

    private void clearMapMarkers() {
        fieldsLayer.clear();
    }

    private void scrollToCard(javafx.scene.Node card) {
        Platform.runLater(() -> {
            var contentBounds = fieldsList.getBoundsInLocal();
            var nodeBounds = card.getBoundsInParent();
            var viewport = scrollPane.getViewportBounds();

            double contentHeight = contentBounds.getHeight();
            double viewportHeight = viewport.getHeight();
            if (contentHeight <= viewportHeight) return;

            double y = nodeBounds.getMinY();
            double v = y / (contentHeight - viewportHeight);
            v = Math.max(0, Math.min(1, v));

            scrollPane.setVvalue(v);
        });
    }

    @FXML
    private void backToHome() {
        switchTo("Home Player");
    }

    @FXML
    private void goToFieldBooking() {
        String fieldId = selectedFieldId.get();
        if (fieldId == null) {
            showInfo("Seleziona un campo prima di andare avanti.");
            return;
        }

        AppContext.setFieldId(fieldId);
        switchTo("Field Booking");
    }

    @Override
    public void reset() {
        addressField.clear();
        cityField.clear();
        cardCtrlById.clear();
        fieldsList.getChildren().clear();

        clearMapMarkers();

        setLoading(false);

        selectedFieldId.set(null);

        setNodeVisibility(errorLabel, false);
    }
}