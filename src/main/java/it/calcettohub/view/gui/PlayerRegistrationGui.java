package it.calcettohub.view.gui;

import it.calcettohub.bean.RegisterPlayerBean;
import it.calcettohub.controller.RegistrationController;
import it.calcettohub.exceptions.EmailAlreadyExistsException;
import it.calcettohub.model.PlayerPosition;
import it.calcettohub.util.PasswordUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.time.format.DateTimeParseException;

public class PlayerRegistrationGui extends BaseFormerGui {
    @FXML private ImageView eyeImageView;
    @FXML private ImageView eyeConfirmImageView;
    @FXML private Label errorLabel;
    @FXML private TextField nameField;
    @FXML private TextField surnameField;
    @FXML private TextField emailField;
    @FXML private TextField passwordTextField;
    @FXML private TextField confirmPasswordTextField;
    @FXML private TextField positionField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private DatePicker dateOfBirthField;
    @FXML private StackPane registerBox;
    @FXML private StackPane successBox;

    private boolean isVisible = false;
    private boolean isConfirmPasswordVisible = false;
    private final RegistrationController controller = new RegistrationController();

    @FXML
    public void initialize() {
        PasswordUtils.bindPasswordFields(passwordField, passwordTextField, isVisible);
        PasswordUtils.bindPasswordFields(confirmPasswordField, confirmPasswordTextField, isConfirmPasswordVisible);
        setEyeIcon();
        setNodeVisibility(errorLabel, false);
        setupResponsiveLabel(40.0);
    }

    @FXML
    private void togglePasswordVisibility(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        ImageView clickedEye = (ImageView) clickedButton.getGraphic();

        if (clickedEye == eyeImageView) {
            isVisible = PasswordUtils.togglePasswordVisibility(passwordField, passwordTextField, isVisible);
        } else if (clickedEye == eyeConfirmImageView) {
            isConfirmPasswordVisible = PasswordUtils.togglePasswordVisibility(confirmPasswordField, confirmPasswordTextField, isConfirmPasswordVisible);
        }
        setEyeIcon();
    }


    private void setEyeIcon() {
        PasswordUtils.updateEyeIcon(eyeImageView, isVisible);
        PasswordUtils.updateEyeIcon(eyeConfirmImageView, isConfirmPasswordVisible);
    }

    @FXML
    private void handleRegister() {
        RegisterPlayerBean bean = new RegisterPlayerBean();

        try {
            validateField(()-> bean.setName(nameField.getText().trim()));
            validateField(()-> bean.setSurname(surnameField.getText().trim()));
            validateField(()-> bean.setDateOfBirth(dateOfBirthField.getValue()));
            validateField(()-> bean.setEmail(emailField.getText().trim()));
            validateField(()-> bean.setPassword(isVisible ? passwordTextField.getText().trim() : passwordField.getText().trim()));
            validateField(()-> bean.setConfirmPassword(isConfirmPasswordVisible ? confirmPasswordTextField.getText().trim() : confirmPasswordField.getText().trim()));
            validateField(()-> bean.setPreferredPosition(PlayerPosition.fromString(positionField.getText().trim())));

            controller.registerPlayer(bean);

            setNodeVisibility(registerBox, false);
            setNodeVisibility(successBox, true);
        } catch (EmailAlreadyExistsException | IllegalArgumentException | DateTimeParseException e) {
            setErrorMessage(errorLabel, e.getMessage());
            showError(errorLabel);
        }
    }

    @Override
    public void reset() {
        nameField.clear();
        surnameField.clear();
        dateOfBirthField.setValue(null);
        emailField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
        positionField.clear();
        setNodeVisibility(errorLabel, false);

        isVisible = false;
        setNodeVisibility(passwordTextField, false);
        setNodeVisibility(passwordField, true);

        isConfirmPasswordVisible = false;
        setNodeVisibility(confirmPasswordTextField, false);
        setNodeVisibility(confirmPasswordField, true);

        setNodeVisibility(registerBox, true);
        setNodeVisibility(successBox, false);
        setEyeIcon();
    }

    @FXML
    private void showDatePicker() {
        dateOfBirthField.show();
    }

    @FXML
    public void goToLogin() {
        switchTo("Login", "Altro");
    }
}