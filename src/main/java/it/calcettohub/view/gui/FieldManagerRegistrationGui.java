package it.calcettohub.view.gui;

import it.calcettohub.bean.RegisterFieldManagerBean;
import it.calcettohub.controller.RegistrationController;
import it.calcettohub.exceptions.EmailAlreadyExistsException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import it.calcettohub.util.Navigator;
import it.calcettohub.util.PasswordUtils;
import javafx.scene.layout.StackPane;

import java.time.LocalDate;

public class FieldManagerRegistrationGui extends BaseFormerGui {
    @FXML private ImageView eyeImageView;
    @FXML private ImageView eyeConfirmImageView;
    @FXML private Label errorLabel;
    @FXML private TextField nameField;
    @FXML private TextField surnameField;
    @FXML private TextField emailField;
    @FXML private TextField passwordTextField;
    @FXML private TextField confirmPasswordTextField;
    @FXML private TextField vatNumberField;
    @FXML private TextField phoneField;
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
        hideError(errorLabel);
        setupResponsiveLabel(40.0);
        //configureButtons();
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
        RegisterFieldManagerBean bean = new RegisterFieldManagerBean();

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

            String vatNumber = vatNumberField.getText().trim();
            bean.setVatNumber(vatNumber);

            String phone = phoneField.getText().trim();
            bean.setPhoneNumber(phone);

            controller.registerFieldManager(bean);

            registerBox.setVisible(false);
            registerBox.setManaged(false);
            successBox.setVisible(true);
            successBox.setManaged(true);
        } catch (EmailAlreadyExistsException | IllegalArgumentException e) {
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
        vatNumberField.clear();
        phoneField.clear();
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