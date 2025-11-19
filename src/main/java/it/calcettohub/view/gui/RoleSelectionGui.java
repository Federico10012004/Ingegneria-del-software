package it.calcettohub.view.gui;

import it.calcettohub.model.Role;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import it.calcettohub.util.Navigator;

public class RoleSelectionGui {

    @FXML private StackPane root;
    @FXML private Button playerButton;
    @FXML private Button managerButton;
    @FXML private Button refereeButton;
    @FXML private ImageView playerIcon;
    @FXML private ImageView refereeIcon;
    @FXML private ImageView managerIcon;
    @FXML private Label playerLabel;
    @FXML private Label managerLabel;
    @FXML private Label refereeLabel;
    @FXML private Label sloganLabel;
    @FXML private Label roleLabel;
    @FXML private Group logoGroup;

    protected static final String FONT_STYLE_TEMPLATE =
            "-fx-font-size: %.1fpx; -fx-font-family: 'Impact'; -fx-font-style: normal;";

    @FXML
    public void initialize() {
        playerButton.setUserData(Role.PLAYER);
        managerButton.setUserData(Role.FIELDMANAGER);

        setupResponsiveButtons();
        setupResponsiveHeader();
    }

    private void setupResponsiveButtons() {
        // Binding per larghezza bottoni (300px base, fino a 600px)
        playerButton.prefWidthProperty().bind(
                root.widthProperty().multiply(0.100).add(0)
        );
        playerButton.minWidthProperty().bind(
                root.widthProperty().multiply(0.375).add(0)
        );

        managerButton.prefWidthProperty().bind(
                root.widthProperty().multiply(0.100).add(0)
        );
        managerButton.minWidthProperty().bind(
                root.widthProperty().multiply(0.375).add(0)
        );

        refereeButton.prefWidthProperty().bind(
                root.widthProperty().multiply(0.100).add(0)
        );
        refereeButton.minWidthProperty().bind(
                root.widthProperty().multiply(0.375).add(0)
        );

        // Binding per altezza bottoni (100px base, fino a 200px)
        playerButton.prefHeightProperty().bind(
                root.heightProperty().multiply(0.120).add(0)
        );
        managerButton.prefHeightProperty().bind(
                root.heightProperty().multiply(0.120).add(0)
        );
        refereeButton.prefHeightProperty().bind(
                root.heightProperty().multiply(0.120).add(0)
        );

        // Binding per dimensioni icone (scalano da 60 a 120)
        playerIcon.fitWidthProperty().bind(
                root.widthProperty().multiply(0.050).add(0)
        );
        playerIcon.fitHeightProperty().bind(
                root.widthProperty().multiply(0.050).add(0)
        );

        managerIcon.fitWidthProperty().bind(
                root.widthProperty().multiply(0.050).add(0)
        );
        managerIcon.fitHeightProperty().bind(
                root.widthProperty().multiply(0.050).add(0)
        );

        refereeIcon.fitWidthProperty().bind(
                root.widthProperty().multiply(0.050).add(0)
        );
        refereeIcon.fitHeightProperty().bind(
                root.widthProperty().multiply(0.050).add(0)
        );

        // Binding per font size delle label (scala da 32 a 50)
        playerLabel.styleProperty().bind(
                root.widthProperty().divide(38).asString(java.util.Locale.US, FONT_STYLE_TEMPLATE)
        );
        managerLabel.styleProperty().bind(
                root.widthProperty().divide(38).asString(java.util.Locale.US, FONT_STYLE_TEMPLATE)
        );
        refereeLabel.styleProperty().bind(
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
        Role selectedRole = (Role) clicked.getUserData();

        Navigator.setUserType(selectedRole);
        Navigator.show("Login");
    }
}