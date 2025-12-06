package it.calcettohub.view.gui;

import javafx.application.Platform;
import javafx.beans.binding.DoubleBinding;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import it.calcettohub.util.Navigator;

import java.util.Locale;
import java.util.Optional;

public abstract class BaseFormerGui implements Resettable {

    @FXML protected Group logoGroup;
    @FXML protected Label sloganLabel;
    @FXML protected Label welcomeLabel;
    @FXML protected GridPane root;

    protected static final String FONT_STYLE_SLOGAN =
            "-fx-font-size: %.1fpx; -fx-font-family: 'System'; -fx-font-style: italic;";
    protected static final String FONT_STYLE_TAHOMA =
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

    protected void bindResponsiveLogo(Group logo, double baseWidth) {
        Platform.runLater(() -> {
            Stage stage = Navigator.getMainStage();
            if (stage == null) return;

            logo.scaleXProperty().bind(stage.widthProperty().divide(baseWidth));
            logo.scaleYProperty().bind(stage.widthProperty().divide(baseWidth));
        });
    }

    protected void setupResponsiveLabel(Label label, Region rootNode, double divisor, String style) {
        if (label != null && rootNode != null) {
            label.styleProperty().bind(
                    rootNode.widthProperty().divide(divisor).asString(Locale.US, style)
            );
        }
    }

    protected void setUpResponsiveNode(Region node, Region rootNode, double multiple) {
        node.prefWidthProperty().bind(
                rootNode.widthProperty().multiply(multiple).add(0)
        );

        node.prefHeightProperty().bind(
                rootNode.heightProperty().multiply(multiple).add(0)
        );
    }

    protected void setUpResponsiveIcon(ImageView icon, Region rootNode, double multiple) {
        DoubleBinding size = rootNode.widthProperty().multiply(multiple);

        icon.fitWidthProperty().bind(size);
        icon.fitHeightProperty().bind(size);
    }

    protected void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Informazione");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    protected void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    protected boolean showConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    protected void switchTo(String screen, String previous) {
        Navigator.setPreviousPage(previous);
        Navigator.show(screen);
    }
}
