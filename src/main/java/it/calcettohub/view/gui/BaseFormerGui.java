package it.calcettohub.view.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import it.calcettohub.util.Navigator;

import java.util.Locale;

public abstract class BaseFormerGui implements Resettable {

    @FXML protected Group logoGroup;
    @FXML protected Label sloganLabel;
    @FXML protected Label welcomeLabel;
    @FXML protected Label successRegister;
    @FXML protected javafx.scene.layout.GridPane root;

    protected void validateField(Runnable setter) {
        setter.run();
    }

    protected void setErrorMessage(Label label, String message) {
        label.setText(message);
    }

    protected void showError(Label label) {
        label.setVisible(true);
        label.setManaged(true);
    }

    protected void hideError(Label label) {
        label.setVisible(false);
        label.setManaged(false);
    }

    protected void setNodeVisibility(Node node, boolean visible) {
        node.setVisible(visible);
        node.setManaged(visible);
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
        if (successRegister != null)
            successRegister.styleProperty().bind(
                    root.widthProperty().divide(30.0).asString(Locale.US,
                            "-fx-font-size: %.1fpx; -fx-font-family: 'Tahoma'; -fx-font-weight: bold;")
            );
    }

    protected void switchTo(String screen, String previous) {
        Navigator.setPreviousPage(previous);
        Navigator.show(screen);
    }
}
