/*package it.calcettohub.view.gui;

import it.calcettohub.util.Navigator;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class DatiCampoController extends BaseFormerGui {

    @FXML private Label emptyFieldError1;
    @FXML private Label emptyFieldError2;
    @FXML private TextField ivaField;
    @FXML private TextField numField;
    @FXML private TextField addressField;
    @FXML private TextField cityField;
    @FXML private TextField capField;
    @FXML private TextField phoneField;
    @FXML private TextField supField;
    @FXML private TextField priceField;
    @FXML private TextField dayField;
    @FXML private TextField timeField;
    @FXML private VBox datiCampo1;
    @FXML private VBox datiCampo2;

    @FXML
    public void initialize() {
        hideAllErrors(emptyFieldError1, emptyFieldError2);
        setupResponsiveLabel(35.0);
    }

    @Override
    public void reset() {
        if ("Dati Campo".equals(Navigator.getPreviousPage())) {
            hideAllErrors(emptyFieldError1, emptyFieldError2);
            return;
        }

        ivaField.clear();
        numField.clear();
        cityField.clear();
        addressField.clear();
        capField.clear();
        phoneField.clear();
        supField.clear();
        priceField.clear();
        dayField.clear();
        timeField.clear();
    }

    @FXML
    public void handleBack() {
        Navigator.setPreviousPage("Dati Campo");
        Navigator.show("Register");
    }

    @FXML
    public void goBack() {
        datiCampo1.setVisible(true);
        datiCampo1.setManaged(true);

        datiCampo2.setVisible(false);
        datiCampo2.setManaged(false);
    }

    @FXML
    public void goAhead() {
        datiCampo2.setVisible(true);
        datiCampo2.setManaged(true);

        datiCampo1.setVisible(false);
        datiCampo1.setManaged(false);
    }
}*/