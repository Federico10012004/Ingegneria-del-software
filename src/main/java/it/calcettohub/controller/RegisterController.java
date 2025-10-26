package it.calcettohub.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import it.calcettohub.core.Navigator;
import it.calcettohub.dao.RegisterDao;
import it.calcettohub.util.PasswordUtils;
import javafx.scene.layout.StackPane;

import java.time.LocalDate;

public class RegisterController extends BaseFormerController {
    @FXML private ImageView eyeImageView;
    @FXML private ImageView eyeConfirmImageView;
    @FXML private Label userError;
    @FXML private Label emptyFieldError;
    @FXML private Label passwordError;
    @FXML private Label linkLabel;
    @FXML private TextField emailField;
    @FXML private TextField passwordTextField;
    @FXML private TextField confirmPasswordTextField;
    @FXML private TextField userField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private DatePicker dataNascita;
    @FXML private StackPane registerBox;
    @FXML private StackPane successBox;
    @FXML private Button goButton;
    @FXML private Button registerButton;

    private boolean isVisible = false;
    private boolean isConfirmPasswordVisible = false;
    private final RegisterDao registerDao = new RegisterDao();
    private String userType;

    @FXML
    public void initialize() {
        userType = Navigator.getUserType();
        PasswordUtils.bindPasswordFields(passwordField, passwordTextField, isVisible);
        PasswordUtils.bindPasswordFields(confirmPasswordField, confirmPasswordTextField, isConfirmPasswordVisible);
        setEyeIcon();
        hideAllErrors(passwordError, emptyFieldError, userError);
        setupResponsiveLabel(40.0);
        configureButtons();
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


    private void configureButtons() {
        if ("gestore".equals(userType)) {
            goButton.setVisible(true);
            goButton.setManaged(true);
            registerButton.setVisible(false);
            registerButton.setManaged(false);
            linkLabel.setVisible(false);
            linkLabel.setManaged(false);
        } else {
            goButton.setVisible(false);
            goButton.setManaged(false);
            registerButton.setVisible(true);
            registerButton.setManaged(true);
            linkLabel.setVisible(true);
            linkLabel.setManaged(true);
        }
    }

    @FXML
    private void handleRegister() {
        String user = userField.getText().trim();
        LocalDate date = dataNascita.getValue();
        String email = emailField.getText().trim();
        String password = isVisible ? passwordTextField.getText().trim() : passwordField.getText().trim();
        String confirmPassword = isConfirmPasswordVisible ? confirmPasswordTextField.getText().trim() : confirmPasswordField.getText().trim();

        int result = registerDao.checkField(user, date, email, password, confirmPassword);
        switch (result) {
            case 1 -> showError(emptyFieldError, passwordError, userError);
            case -1 -> showError(passwordError, emptyFieldError, userError);
            case -2 -> showError(userError, emptyFieldError, passwordError);
            case 0 -> {
                registerBox.setVisible(false);
                registerBox.setManaged(false);
                successBox.setVisible(true);
                successBox.setManaged(true);
            }
            default -> System.err.println("Errore inatteso nella registrazione del giocatore");
        }
    }

    @FXML
    private void handleGoTo() {
        String user = userField.getText().trim();
        LocalDate date = dataNascita.getValue();
        String email = emailField.getText().trim();
        String password = isVisible ? passwordTextField.getText().trim() : passwordField.getText().trim();
        String confirmPassword = isConfirmPasswordVisible ? confirmPasswordTextField.getText().trim() : confirmPasswordField.getText().trim();

        int result = registerDao.checkField(user, date, email, password, confirmPassword);
        switch (result) {
            case 1 -> showError(emptyFieldError, passwordError, userError);
            case -1 -> showError(passwordError, emptyFieldError, userError);
            case -2 -> showError(userError, emptyFieldError, passwordError);
            case 0 -> Navigator.show("Dati Campo"); // vai alla schermata del campo
            default -> System.err.println("Errore inatteso nella registrazione del gestore");
        }
    }


    @Override
    public void reset() {
        userType = Navigator.getUserType();
        configureButtons();

        if ("Dati Campo".equals(Navigator.getPreviousPage())) {
            hideAllErrors(passwordError, emptyFieldError, userError);
            return;
        }

        userField.clear();
        dataNascita.setValue(null);
        emailField.clear();
        passwordField.clear();
        confirmPasswordField.clear();
        hideAllErrors(passwordError, emptyFieldError, userError);

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
        dataNascita.show();
    }

    @FXML
    public void handleCancel() {
        Navigator.setPreviousPage("Altro");
        Navigator.show("Login");
    }
}
