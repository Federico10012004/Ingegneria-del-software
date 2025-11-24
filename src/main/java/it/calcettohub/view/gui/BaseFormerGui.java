package it.calcettohub.view.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import it.calcettohub.util.Navigator;

import java.util.Locale;

public abstract class BaseFormerGui implements Resettable {

    @FXML protected Group logoGroup;
    @FXML protected Label sloganLabel;
    @FXML protected Label welcomeLabel;
    @FXML protected GridPane root;

    protected static final String FONT_STYLE_SLOGAN =
            "-fx-font-size: %.1fpx; -fx-font-family: 'System'; -fx-font-style: italic;";
    protected static final String FONT_STYLE_WELCOME =
            "-fx-font-size: %.1fpx; -fx-font-family: 'Tahoma'; -fx-font-weight: bold;";
    protected static final String FONT_STYLE_SUCCESS_REGISTER =
            "-fx-font-size: %.1fpx; -fx-font-family: 'Tahoma'; -fx-font-weight: bold;";

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

    protected void setNodeVisibility(Node node, boolean visible) {
        node.setVisible(visible);
        node.setManaged(visible);
    }

    protected void bindResponsiveLogo(Group Logo, double baseWidth) {
        Platform.runLater(() -> {
            Stage stage = Navigator.getMainStage();
            if (stage == null) return;

            Logo.scaleXProperty().bind(stage.widthProperty().divide(baseWidth));
            Logo.scaleYProperty().bind(stage.widthProperty().divide(baseWidth));
        });
    }

    protected void setupResponsiveLabel(Label label, Region rootNode, double divisor, String style) {
        if (label != null && rootNode != null) {
            label.styleProperty().bind(
                    rootNode.widthProperty().divide(divisor).asString(Locale.US, style)
            );
        }
    }

    protected void switchTo(String screen, String previous) {
        Navigator.setPreviousPage(previous);
        Navigator.show(screen);
    }
}
