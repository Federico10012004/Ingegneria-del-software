package it.calcettohub.view.gui;

import it.calcettohub.bean.RegisterFieldManagerBean;
import it.calcettohub.bean.RegisterPlayerBean;
import it.calcettohub.bean.RegistrationBean;
import it.calcettohub.controller.RegistrationController;
import it.calcettohub.exceptions.EmailAlreadyExistsException;
import it.calcettohub.model.PlayerPosition;
import it.calcettohub.model.Role;
import it.calcettohub.util.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import it.calcettohub.util.PasswordUtils;
import javafx.scene.layout.StackPane;

import java.time.format.DateTimeParseException;

public class RegistrationGui extends BaseFormerGui {
    @FXML private ImageView eyeImageView;
    @FXML private ImageView eyeConfirmImageView;
    @FXML private ImageView vatNumberImageView;
    @FXML private ImageView positionImageView;
    @FXML private Label errorLabel;
    @FXML private Label successRegister;
    @FXML private TextField nameField;
    @FXML private TextField surnameField;
    @FXML private TextField emailField;
    @FXML private TextField passwordTextField;
    @FXML private TextField confirmPasswordTextField;
    @FXML private TextField vatNumberField;
    @FXML private TextField phoneField;
    @FXML private TextField positionField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private DatePicker dateOfBirthField;
    @FXML private StackPane phonePane;

    private boolean isVisible = false;
    private boolean isConfirmPasswordVisible = false;
    private final RegistrationController controller = new RegistrationController();

    @FXML
    private void initialize() {
        disableSessionCheck();

        PasswordUtils.bindPasswordFields(passwordField, passwordTextField, isVisible);
        PasswordUtils.bindPasswordFields(confirmPasswordField, confirmPasswordTextField, isConfirmPasswordVisible);

        setEyeIcon();

        setNodeVisibility(errorLabel, false);

        bindResponsiveLogo(logoGroup, 900.0);
        setupResponsiveLabel(sloganLabel, root, 60.0, FONT_STYLE_SLOGAN);
        setupResponsiveLabel(welcomeLabel, root, 40.0, FONT_STYLE_TAHOMA);
        setupResponsiveLabel(successRegister, root, 30.0, FONT_STYLE_TAHOMA);

        setUpRoleVisibility();
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
        try {
            if (Navigator.getUserType() == Role.PLAYER) {
                RegisterPlayerBean bean = new RegisterPlayerBean();

                validateCommonFields(bean);
                validateField(()-> bean.setPreferredPosition(PlayerPosition.fromString(positionField.getText().trim())));

                controller.registerPlayer(bean);
            } else {
                RegisterFieldManagerBean bean = new RegisterFieldManagerBean();

                validateCommonFields(bean);
                validateField(()-> bean.setVatNumber(vatNumberField.getText().trim()));
                validateField(()-> bean.setPhoneNumber(phoneField.getText().trim()));

                controller.registerFieldManager(bean);
            }

            showInfo("Registrazione completata con successo.");
            goToLogin();
        } catch (EmailAlreadyExistsException | IllegalArgumentException | DateTimeParseException e) {
            setErrorMessage(errorLabel, e.getMessage());
            showError(errorLabel);
        }
    }

    private void validateCommonFields(RegistrationBean bean) {
        validateField(()-> bean.setName(nameField.getText().trim()));
        validateField(()-> bean.setSurname(surnameField.getText().trim()));
        validateField(()-> bean.setDateOfBirth(dateOfBirthField.getValue()));
        validateField(()-> bean.setEmail(emailField.getText().trim()));
        validateField(()-> bean.setPassword(isVisible ? passwordTextField.getText().trim() : passwordField.getText().trim()));
        validateField(()-> bean.setConfirmPassword(isConfirmPasswordVisible ? confirmPasswordTextField.getText().trim() : confirmPasswordField.getText().trim()));
    }

    private void setUpRoleVisibility() {
        if (Navigator.getUserType() == Role.PLAYER) {
            setNodeVisibility(positionField, true);
            setNodeVisibility(positionImageView, true);

            setNodeVisibility(vatNumberField, false);
            setNodeVisibility(vatNumberImageView, false);
            setNodeVisibility(phonePane, false);
        } else {
            setNodeVisibility(positionField, false);
            setNodeVisibility(positionImageView, false);

            setNodeVisibility(vatNumberField, true);
            setNodeVisibility(vatNumberImageView, true);
            setNodeVisibility(phonePane, true);
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
        positionField.clear();
        setNodeVisibility(errorLabel, false);

        isVisible = false;
        setNodeVisibility(passwordTextField, false);
        setNodeVisibility(passwordField, true);

        isConfirmPasswordVisible = false;
        setNodeVisibility(confirmPasswordTextField, false);
        setNodeVisibility(confirmPasswordField, true);
        setEyeIcon();

        setUpRoleVisibility();
    }

    @FXML
    private void showDatePicker() {
        dateOfBirthField.show();
    }

    @FXML
    private void goToLogin() {
        switchTo("Login", "Altro");
    }
}