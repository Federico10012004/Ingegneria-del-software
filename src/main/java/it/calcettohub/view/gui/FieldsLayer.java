package it.calcettohub.view.gui;

import com.gluonhq.maps.MapLayer;
import it.calcettohub.bean.FieldMapBean;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.shape.Circle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class FieldsLayer extends MapLayer {

    private final Map<String, FieldMapBean> itemById = new HashMap<>();
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

    public void setItems(List<FieldMapBean> items) {
        getChildren().clear();
        itemById.clear();
        markerById.clear();

        for (FieldMapBean fmb : items) {
            if (fmb.getLat() == null || fmb.getLon() == null) continue;

            itemById.put(fmb.getFieldId(), fmb);

            Circle marker = new Circle(7);
            marker.getStyleClass().add("marker");
            marker.setOnMouseClicked(_ -> onSelect.accept(fmb.getFieldId()));

            markerById.put(fmb.getFieldId(), marker);
            getChildren().add(marker);
        }

        markDirty();
    }

    public Node getMarker(String fieldId) {
        return markerById.get(fieldId);
    }

    public FieldMapBean getItem(String fieldId) {
        return itemById.get(fieldId);
    }

    @Override
    protected void layoutLayer() {
        for (FieldMapBean fmb : itemById.values()) {
            Node marker = markerById.get(fmb.getFieldId());
            if (marker == null) continue;

            Point2D p = getMapPoint(fmb.getLat(), fmb.getLon());

            marker.setTranslateX(p.getX());
            marker.setTranslateY(p.getY());
        }
    }
}