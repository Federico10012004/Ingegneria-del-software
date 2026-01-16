package it.calcettohub.view.gui;

import it.calcettohub.bean.BookingViewBean;
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

    public void setData(BookingViewBean booking, Runnable onCancel) {
        fieldNameLabel.setText(booking.getBvFieldName());
        dateLabel.setText(booking.getBvDate().toString());
        startTimeLabel.setText(booking.getBvStart().toString());
        endTimeLabel.setText(booking.getBvEnd().toString());
        statusLabel.setText(booking.getBvStatus());

        cancelButton.setOnAction(_ -> onCancel.run());
    }
}