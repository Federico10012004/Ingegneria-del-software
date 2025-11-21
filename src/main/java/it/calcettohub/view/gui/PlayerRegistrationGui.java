package it.calcettohub.view.gui;

import it.calcettohub.bean.RegisterPlayerBean;
import it.calcettohub.controller.RegistrationController;
import it.calcettohub.exceptions.EmailAlreadyExistsException;
import it.calcettohub.model.PlayerPosition;
import it.calcettohub.util.Navigator;
import it.calcettohub.util.PasswordUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.time.LocalDate;
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
        hideError(errorLabel);
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
            String name = nameField.getText().trim();
            bean.setName(name);

            String surname = surnameField.getText().trim();
            bean.setSurname(surname);

            LocalDate dateOfBirth = dateOfBirthField.getValue();
            bean.setDateOfBirth(dateOfBirth);

            String email = emailField.getText().trim();
            bean.setEmail(email);

            String password = isVisible ? passwordTextField.getText().trim() : passwordField.getText().trim();
            bean.setPassword(password);

            String confirmPassword = isConfirmPasswordVisible ? confirmPasswordTextField.getText().trim() : confirmPasswordField.getText().trim();
            bean.setConfirmPassword(confirmPassword);

            PlayerPosition position = PlayerPosition.fromString(positionField.getText().trim());
            bean.setPreferredPosition(position);

            controller.registerPlayer(bean);

            registerBox.setVisible(false);
            registerBox.setManaged(false);
            successBox.setVisible(true);
            successBox.setManaged(true);
        } catch (EmailAlreadyExistsException | IllegalArgumentException | DateTimeParseException e) {
            errorLabel.setText(e.getMessage());
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
        hideError(errorLabel);

        isVisible = false;
        passwordTextField.setVisible(false);
        passwordTextField.setManaged(false);
        passwordField.setVisible(true);
        passwordField.setManaged(true);

        isConfirmPasswordVisible = false;
        confirmPasswordTextField.setVisible(false);
        confirmPasswordTextField.setManaged(false);
        confirmPasswordField.setVisible(true);
        confirmPasswordField.setManaged(true);

        registerBox.setVisible(true);
        registerBox.setManaged(true);
        successBox.setVisible(false);
        successBox.setManaged(false);
        setEyeIcon();
    }

    @FXML
    private void showDatePicker() {
        dateOfBirthField.show();
    }

    @FXML
    public void goToLogin() {
        Navigator.setPreviousPage("Altro");
        Navigator.show("Login");
    }
}