package it.uniroma2.dicii.CalcettoHub.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import it.uniroma2.dicii.CalcettoHub.core.Navigator;

public class WelcomeController {

    public Label titleLabel;

    @FXML private StackPane root;
    @FXML private Button giocatoreButton;
    @FXML private Button gestoreButton;
    @FXML private Button arbitroButton;
    @FXML private ImageView giocatoreIcon;
    @FXML private ImageView arbitroIcon;
    @FXML private ImageView gestoreIcon;
    @FXML private Label giocatoreLabel;
    @FXML private Label proprietarioLabel;
    @FXML private Label arbitroLabel;
    @FXML private Label sloganLabel;
    @FXML private Label roleLabel;
    @FXML private Group logoGroup;

    protected static final String FONT_STYLE_TEMPLATE =
            "-fx-font-size: %.1fpx; -fx-font-family: 'Impact'; -fx-font-style: normal;";

    @FXML
    public void initialize() {
        setupResponsiveButtons();
        setupResponsiveHeader();
    }

    private void setupResponsiveButtons() {
        // Binding per larghezza bottoni (300px base, fino a 600px)
        giocatoreButton.prefWidthProperty().bind(
                root.widthProperty().multiply(0.100).add(0)
        );
        giocatoreButton.minWidthProperty().bind(
                root.widthProperty().multiply(0.375).add(0)
        );

        gestoreButton.prefWidthProperty().bind(
                root.widthProperty().multiply(0.100).add(0)
        );
        gestoreButton.minWidthProperty().bind(
                root.widthProperty().multiply(0.375).add(0)
        );

        arbitroButton.prefWidthProperty().bind(
                root.widthProperty().multiply(0.100).add(0)
        );
        arbitroButton.minWidthProperty().bind(
                root.widthProperty().multiply(0.375).add(0)
        );

        // Binding per altezza bottoni (100px base, fino a 200px)
        giocatoreButton.prefHeightProperty().bind(
                root.heightProperty().multiply(0.120).add(0)
        );
        gestoreButton.prefHeightProperty().bind(
                root.heightProperty().multiply(0.120).add(0)
        );
        arbitroButton.prefHeightProperty().bind(
                root.heightProperty().multiply(0.120).add(0)
        );

        // Binding per dimensioni icone (scalano da 60 a 120)
        giocatoreIcon.fitWidthProperty().bind(
                root.widthProperty().multiply(0.050).add(0)
        );
        giocatoreIcon.fitHeightProperty().bind(
                root.widthProperty().multiply(0.050).add(0)
        );

        gestoreIcon.fitWidthProperty().bind(
                root.widthProperty().multiply(0.050).add(0)
        );
        gestoreIcon.fitHeightProperty().bind(
                root.widthProperty().multiply(0.050).add(0)
        );

        arbitroIcon.fitWidthProperty().bind(
                root.widthProperty().multiply(0.050).add(0)
        );
        arbitroIcon.fitHeightProperty().bind(
                root.widthProperty().multiply(0.050).add(0)
        );

        // Binding per font size delle label (scala da 32 a 50)
        giocatoreLabel.styleProperty().bind(
                root.widthProperty().divide(38).asString(java.util.Locale.US, FONT_STYLE_TEMPLATE)
        );
        proprietarioLabel.styleProperty().bind(
                root.widthProperty().divide(38).asString(java.util.Locale.US, FONT_STYLE_TEMPLATE)
        );
        arbitroLabel.styleProperty().bind(
                root.widthProperty().divide(38).asString(java.util.Locale.US, FONT_STYLE_TEMPLATE)
        );
    }

    private void setupResponsiveHeader() {
        Platform.runLater(() -> {
            Stage stage = (Stage) root.getScene().getWindow();

            // Ridimensionamento del logo (Group o ImageView)
            logoGroup.scaleXProperty().bind(stage.widthProperty().divide(1000));
            logoGroup.scaleYProperty().bind(stage.widthProperty().divide(1000));
        });

        // Label "Scegli il tuo ruolo" → grassetto e dinamica
        roleLabel.styleProperty().bind(
                root.widthProperty().divide(35)
                        .asString(java.util.Locale.US, "-fx-font-size: %.1fpx; -fx-font-family: 'Impact'; -fx-font-weight: bold;")
        );

        // Slogan → corsivo e dinamico
        sloganLabel.styleProperty().bind(
                root.widthProperty().divide(50)
                        .asString(java.util.Locale.US, "-fx-font-size: %.1fpx; -fx-font-family: 'System'; -fx-font-style: italic;")
        );
    }


    @FXML
    private void switchToLogin(ActionEvent event) {
        Button clicked = (Button) event.getSource();
        String tipo = clicked.getAccessibleText();
        Navigator.setUserType(tipo);
        Navigator.show("Login");
    }
}