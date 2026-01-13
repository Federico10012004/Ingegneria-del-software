package it.calcettohub.view.gui;

import it.calcettohub.model.Field;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class FieldCardGui {
    @FXML private Label nameLabel;
    @FXML private Label addressLabel;
    @FXML private Label cityLabel;
    @FXML private Label surfaceLabel;
    @FXML private Label indoorLabel;
    @FXML private Label priceLabel;
    @FXML private Button deleteButton;

    public void setData(Field field, Runnable onDelete) {
        nameLabel.setText(field.getFieldName());
        addressLabel.setText(field.getAddress());
        cityLabel.setText(field.getCity());
        surfaceLabel.setText(field.getSurfaceType().toString());
        indoorLabel.setText(field.isIndoor() ? "Indoor: si" : "Indoor: no");
        priceLabel.setText(field.getHourlyPrice().toString());

        deleteButton.setOnAction(_ -> onDelete.run());
    }
}