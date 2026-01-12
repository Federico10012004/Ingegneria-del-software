package it.calcettohub.view.gui;

import com.gluonhq.maps.MapLayer;
import it.calcettohub.model.valueobject.FieldMapItem;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.shape.Circle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class FieldsLayer extends MapLayer {

    private final Map<String, FieldMapItem> itemById = new HashMap<>();
    private final Map<String, Node> markerById = new HashMap<>();
    private final Consumer<String> onSelect;

    public FieldsLayer(Consumer<String> onSelect) {
        this.onSelect = onSelect;
    }

    public void refresh() {
        markDirty();
    }

    public void clear() {
        getChildren().clear();
        itemById.clear();
        markerById.clear();
        markDirty();
    }

    public void setItems(List<FieldMapItem> items) {
        getChildren().clear();
        itemById.clear();
        markerById.clear();

        for (FieldMapItem it : items) {
            if (it.lat() == null || it.lon() == null) continue;

            itemById.put(it.id(), it);

            Circle marker = new Circle(7);
            marker.getStyleClass().add("marker");
            marker.setOnMouseClicked(_ -> onSelect.accept(it.id()));

            markerById.put(it.id(), marker);
            getChildren().add(marker);
        }

        markDirty();
    }

    public Node getMarker(String fieldId) {
        return markerById.get(fieldId);
    }

    public FieldMapItem getItem(String fieldId) {
        return itemById.get(fieldId);
    }

    @Override
    protected void layoutLayer() {
        for (FieldMapItem it : itemById.values()) {
            Node marker = markerById.get(it.id());
            if (marker == null) continue;

            Point2D p = getMapPoint(it.lat(), it.lon());

            marker.setTranslateX(p.getX());
            marker.setTranslateY(p.getY());
        }
    }
}