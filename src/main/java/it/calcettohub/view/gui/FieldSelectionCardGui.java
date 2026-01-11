package it.calcettohub.view.gui;

import it.calcettohub.model.Field;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;

public class FieldSelectionCardGui {
    @FXML private Label nameLabel;
    @FXML private Label addressLabel;
    @FXML private Label cityLabel;
    @FXML private Label surfaceLabel;
    @FXML private Label indoorLabel;
    @FXML private Label priceLabel;
    @FXML private GridPane rootPane;

    private static final PseudoClass PC_SELECTED = PseudoClass.getPseudoClass("selected");

    public void setData(Field field, Runnable onSelect) {
        nameLabel.setText(field.getFieldName());
        addressLabel.setText(field.getAddress());
        cityLabel.setText(field.getCity());
        surfaceLabel.setText(field.getSurfaceType().toString());
        indoorLabel.setText(field.isIndoor() ? "Indoor: si" : "Indoor: no");
        priceLabel.setText(field.getHourlyPrice().toString());

        rootPane.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                onSelect.run();
            }
        });
    }

    public void setSelected(boolean selected) {
        rootPane.pseudoClassStateChanged(PC_SELECTED, selected);
    }

    public GridPane getRoot() {
        return rootPane;
    }
}
