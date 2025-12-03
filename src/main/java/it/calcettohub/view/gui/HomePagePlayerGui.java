package it.calcettohub.view.gui;

import it.calcettohub.util.PasswordUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class HomePagePlayerGui extends BaseFormerGui {
    @FXML private ImageView eyeIcon;
    @FXML private ImageView eyeConfirmIcon;
    @FXML private ImageView fieldIcon;
    @FXML private ImageView matchIcon;
    @FXML private Label errorLabel;
    @FXML private Label errorLabel1;
    @FXML private Label accountLabel;
    @FXML private Label passwordLabel;
    @FXML private Label helloLabel;
    @FXML private Label emailLabel;
    @FXML private Label fieldLabel;
    @FXML private Label matchLabel;
    @FXML private TextField nameField;
    @FXML private TextField surnameField;
    @FXML private TextField positionField;
    @FXML private TextField passwordTextField;
    @FXML private TextField confirmPasswordTextField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private DatePicker dateOfBirthField;
    @FXML private Button accountButton;
    @FXML private Button backButton;
    @FXML private Button backButton1;
    @FXML private Button fieldButton;
    @FXML private Button matchButton;
    @FXML private Button confirmModifyButton;
    @FXML private VBox accountPanel;
    @FXML private VBox changePasswordPanel;
    @FXML private VBox organizeMatch;

    private boolean isVisible = false;
    private boolean isConfirmPasswordVisible = false;

    public void start() {

    }

    @FXML
    private void togglePasswordVisibility(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        ImageView clickedEye = (ImageView) clickedButton.getGraphic();

        if (clickedEye == eyeIcon) {
            isVisible = PasswordUtils.togglePasswordVisibility(passwordField, passwordTextField, isVisible);
        } else if (clickedEye == eyeConfirmIcon) {
            isConfirmPasswordVisible = PasswordUtils.togglePasswordVisibility(confirmPasswordField, confirmPasswordTextField, isConfirmPasswordVisible);
        }
        setEyeIcon();
    }

    private void setEyeIcon() {
        PasswordUtils.updateEyeIcon(eyeIcon, isVisible);
        PasswordUtils.updateEyeIcon(eyeConfirmIcon, isConfirmPasswordVisible);
    }

    @Override
    public void reset() {
        passwordField.clear();
        confirmPasswordField.clear();
    }

    @FXML
    private void showDatePicker() {
        dateOfBirthField.show();
    }
}
