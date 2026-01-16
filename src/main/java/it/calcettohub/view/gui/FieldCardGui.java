package it.calcettohub.view.gui;

import it.calcettohub.bean.GetFieldBean;
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

    public void setData(GetFieldBean bean, Runnable onDelete) {
        nameLabel.setText(bean.getFieldName());
        addressLabel.setText(bean.getAddress());
        cityLabel.setText(bean.getCity());
        surfaceLabel.setText(bean.getSurface());
        indoorLabel.setText(bean.isIndoor() ? "Indoor: si" : "Indoor: no");
        priceLabel.setText(bean.getHourlyPrice().toString());

        deleteButton.setOnAction(_ -> onDelete.run());
    }
}