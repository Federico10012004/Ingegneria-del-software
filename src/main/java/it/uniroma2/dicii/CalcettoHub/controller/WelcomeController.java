package main.java.it.uniroma2.dicii.CalcettoHub.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import main.java.it.uniroma2.dicii.CalcettoHub.core.Navigator;

public class WelcomeController {

    public Label titleLabel;

    @FXML private StackPane root;
    @FXML private Button giocatoreButton, proprietarioButton, arbitroButton;
    @FXML private ImageView giocatoreIcon, proprietarioIcon, arbitroIcon;
    @FXML private Label giocatoreLabel, proprietarioLabel, arbitroLabel, sloganLabel, roleLabel;
    @FXML private Group logoGroup;

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

        proprietarioButton.prefWidthProperty().bind(
                root.widthProperty().multiply(0.100).add(0)
        );
        proprietarioButton.minWidthProperty().bind(
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
        proprietarioButton.prefHeightProperty().bind(
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

        proprietarioIcon.fitWidthProperty().bind(
                root.widthProperty().multiply(0.050).add(0)
        );
        proprietarioIcon.fitHeightProperty().bind(
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
                root.widthProperty().divide(38).asString(java.util.Locale.US, "-fx-font-size: %.1fpx; -fx-font-family: 'Impact'; -fx-font-style: normal;")
        );
        proprietarioLabel.styleProperty().bind(
                root.widthProperty().divide(38).asString(java.util.Locale.US, "-fx-font-size: %.1fpx; -fx-font-family: 'Impact'; -fx-font-style: normal;")
        );
        arbitroLabel.styleProperty().bind(
                root.widthProperty().divide(38).asString(java.util.Locale.US, "-fx-font-size: %.1fpx; -fx-font-family: 'Impact'; -fx-font-style: normal;")
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
    private void switchToLogin() {
        Navigator.show("Login");
    }
}