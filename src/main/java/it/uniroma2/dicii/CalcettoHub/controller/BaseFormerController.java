package it.uniroma2.dicii.CalcettoHub.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import it.uniroma2.dicii.CalcettoHub.core.Navigator;

import java.util.Locale;

public abstract class BaseFormerController implements Resettable {

    @FXML protected Group logoGroup;
    @FXML protected Label sloganLabel;
    @FXML protected Label welcomeLabel;
    @FXML protected javafx.scene.layout.GridPane root;

    protected void showError(Label label, Label... allLabels) {
        hideAllErrors(allLabels);
        label.setVisible(true);
        label.setManaged(true);
    }

    protected void hideAllErrors(Label... labels) {
        for (Label l : labels) {
            l.setVisible(false);
            l.setManaged(false);
        }
    }

    protected void setupResponsiveLabel(double fontScaleWelcome) {
        Platform.runLater(() -> {
            Stage stage = Navigator.getMainStage();
            if (stage == null) return;

            double initialScale = stage.getWidth() / 900.0;
            logoGroup.setScaleX(initialScale);
            logoGroup.setScaleY(initialScale);

            logoGroup.scaleXProperty().bind(stage.widthProperty().divide(900.0));
            logoGroup.scaleYProperty().bind(stage.widthProperty().divide(900.0));
        });

        if (sloganLabel != null)
            sloganLabel.styleProperty().bind(
                    root.widthProperty().divide(60.0).asString(Locale.US,
                            "-fx-font-size: %.1fpx; -fx-font-family: 'System'; -fx-font-style: italic;")
            );

        if (welcomeLabel != null)
            welcomeLabel.styleProperty().bind(
                    root.widthProperty().divide(fontScaleWelcome).asString(Locale.US,
                            "-fx-font-size: %.1fpx; -fx-font-family: 'Tahoma'; -fx-font-weight: bold;")
            );
    }


    @Override
    public abstract void reset();
}
