package it.calcettohub.view.gui;

import it.calcettohub.model.valueobject.BookingView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class BookingCardGui {
    @FXML private Label fieldNameLabel;
    @FXML private Label dateLabel;
    @FXML private Label startTimeLabel;
    @FXML private Label endTimeLabel;
    @FXML private Label statusLabel;
    @FXML private Button cancelButton;

    public void setData(BookingView booking, Runnable onCancel) {
        fieldNameLabel.setText(booking.fieldName());
        dateLabel.setText(booking.slot().date().toString());
        startTimeLabel.setText(booking.slot().start().toLocalTime().toString());
        endTimeLabel.setText(booking.slot().end().toLocalTime().toString());
        statusLabel.setText(booking.status().toString());

        cancelButton.setOnAction(_ -> onCancel.run());
    }
}