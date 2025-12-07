package it.calcettohub.view.gui;

import it.calcettohub.model.Role;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import it.calcettohub.util.Navigator;

public class RoleSelectionGui extends BaseFormerGui {

    @FXML private Button playerButton;
    @FXML private Button managerButton;
    @FXML private Button refereeButton;
    @FXML private Label roleLabel;

    @FXML
    private void initialize() {
        disableSessionCheck();

        playerButton.setUserData(Role.PLAYER);
        managerButton.setUserData(Role.FIELDMANAGER);
        refereeButton.setUserData(Role.REFEREE);

        bindResponsiveLogo(logoGroup, 900.00);
        setupResponsiveLabel(roleLabel, root, 30.0, FONT_STYLE_TAHOMA);
        setupResponsiveLabel(sloganLabel, root, 60.0, FONT_STYLE_SLOGAN);
    }

    @Override
    public void reset() {
        Navigator.resetUserType();
    }

    @FXML
    private void switchToLogin(ActionEvent event) {
        Button clicked = (Button) event.getSource();
        Role selectedRole = (Role) clicked.getUserData();

        if (selectedRole == Role.REFEREE) {
            showInfo("Funzionalità non implementata");
            return;
        }

        Navigator.setUserType(selectedRole);
        Navigator.show("Login");
    }
}