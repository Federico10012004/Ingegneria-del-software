package it.uniroma2.dicii.CalcettoHub.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class DatiCampoController extends BaseFormerController {

    @FXML private Label emptyFieldError;
    @FXML private TextField ivaField, nameField, addressField, cityField, capField;
    @FXML private VBox datiCampo1, datiCampo2;

    @FXML
    public void initialize() {
        hideAllErrors(emptyFieldError);
        setupResponsiveLabel(35.0);
    }


}
