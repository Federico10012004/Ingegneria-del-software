package it.calcettohub.view.gui;

import it.calcettohub.model.Role;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import it.calcettohub.util.Navigator;

public class RoleSelectionGui extends BaseFormerGui {

    @FXML private StackPane rootPane;
    @FXML private Button playerButton;
    @FXML private Button managerButton;
    @FXML private Button refereeButton;
    @FXML private ImageView playerIcon;
    @FXML private ImageView refereeIcon;
    @FXML private ImageView managerIcon;
    @FXML private Label playerLabel;
    @FXML private Label managerLabel;
    @FXML private Label refereeLabel;
    @FXML private Label roleLabel;

    private static final String FONT_STYLE_TEMPLATE =
            "-fx-font-size: %.1fpx; -fx-font-family: 'Impact'; -fx-font-style: normal;";
    private static final String FONT_STYLE_ROLE_LABEL =
            "-fx-font-size: %.1fpx; -fx-font-family: 'Impact'; -fx-font-weight: bold;";
    private static final String FONT_STYLE_SLOGAN_LABEL =
            "-fx-font-size: %.1fpx; -fx-font-family: 'System'; -fx-font-style: italic;";

    @FXML
    public void initialize() {
        playerButton.setUserData(Role.PLAYER);
        managerButton.setUserData(Role.FIELDMANAGER);

        setupResponsiveButtons();
        bindResponsiveLogo(logoGroup, 1000.00);
        // Ridimensionamento del logo (Group o ImageView)
        setupResponsiveLabel(roleLabel, rootPane, 35.0, FONT_STYLE_ROLE_LABEL);
        // Label "Scegli il tuo ruolo" → grassetto e dinamica
        setupResponsiveLabel(sloganLabel, rootPane, 50.0, FONT_STYLE_SLOGAN_LABEL);
    }

    private void setupResponsiveButtons() {
        // Binding per larghezza bottoni
        playerButton.prefWidthProperty().bind(
                rootPane.widthProperty().multiply(0.100).add(0)
        );
        playerButton.minWidthProperty().bind(
                rootPane.widthProperty().multiply(0.375).add(0)
        );

        managerButton.prefWidthProperty().bind(
                rootPane.widthProperty().multiply(0.100).add(0)
        );
        managerButton.minWidthProperty().bind(
                rootPane.widthProperty().multiply(0.375).add(0)
        );

        refereeButton.prefWidthProperty().bind(
                rootPane.widthProperty().multiply(0.100).add(0)
        );
        refereeButton.minWidthProperty().bind(
                rootPane.widthProperty().multiply(0.375).add(0)
        );

        // Binding per altezza bottoni (100px base, fino a 200px)
        playerButton.prefHeightProperty().bind(
                rootPane.heightProperty().multiply(0.120).add(0)
        );
        managerButton.prefHeightProperty().bind(
                rootPane.heightProperty().multiply(0.120).add(0)
        );
        refereeButton.prefHeightProperty().bind(
                rootPane.heightProperty().multiply(0.120).add(0)
        );

        // Binding per dimensioni icone (scalano da 60 a 120)
        playerIcon.fitWidthProperty().bind(
                rootPane.widthProperty().multiply(0.050).add(0)
        );
        playerIcon.fitHeightProperty().bind(
                rootPane.widthProperty().multiply(0.050).add(0)
        );

        managerIcon.fitWidthProperty().bind(
                rootPane.widthProperty().multiply(0.050).add(0)
        );
        managerIcon.fitHeightProperty().bind(
                rootPane.widthProperty().multiply(0.050).add(0)
        );

        refereeIcon.fitWidthProperty().bind(
                rootPane.widthProperty().multiply(0.050).add(0)
        );
        refereeIcon.fitHeightProperty().bind(
                rootPane.widthProperty().multiply(0.050).add(0)
        );

        // Binding per font size delle label (scala da 32 a 50)
        playerLabel.styleProperty().bind(
                rootPane.widthProperty().divide(38).asString(java.util.Locale.US, FONT_STYLE_TEMPLATE)
        );
        managerLabel.styleProperty().bind(
                rootPane.widthProperty().divide(38).asString(java.util.Locale.US, FONT_STYLE_TEMPLATE)
        );
        refereeLabel.styleProperty().bind(
                rootPane.widthProperty().divide(38).asString(java.util.Locale.US, FONT_STYLE_TEMPLATE)
        );
    }

    @Override
    public void reset() {
        Navigator.resetUserType();
    }

    @FXML
    private void switchToLogin(ActionEvent event) {
        Button clicked = (Button) event.getSource();
        Role selectedRole = (Role) clicked.getUserData();

        Navigator.setUserType(selectedRole);
        Navigator.show("Login");
    }
}