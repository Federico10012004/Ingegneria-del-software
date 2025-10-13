package it.uniroma2.dicii.CalcettoHub.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.ImageView;
import it.uniroma2.dicii.CalcettoHub.core.Navigator;
import it.uniroma2.dicii.CalcettoHub.dao.RegisterDao;
import it.uniroma2.dicii.CalcettoHub.util.PasswordUtils;

import java.time.LocalDate;

public class RegisterController extends BaseFormerController {
    @FXML private ImageView eyeImageView, eyeConfirmImageView;
    @FXML private Label userError, emptyFieldError, passwordError;
    @FXML private javafx.scene.control.TextField emailField, passwordTextField, confirmPasswordTextField, userField;
    @FXML private PasswordField passwordField, confirmPasswordField;
    @FXML private DatePicker dataNascita;

    private boolean isVisible = false;
    private boolean isConfirmPasswordVisible = false;
    private final RegisterDao registerDao = new RegisterDao();

    @FXML
    public void initialize() {
        PasswordUtils.bindPasswordFields(passwordField, passwordTextField, isVisible);
        PasswordUtils.bindPasswordFields(confirmPasswordField, confirmPasswordTextField, isConfirmPasswordVisible);
        setEyeIcon();
        hideAllErrors(passwordError, emptyFieldError, userError);
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
                hideAllErrors(passwordError, emptyFieldError, userError);
                System.out.println("✅ Registrazione riuscita!");
            }
            default -> System.err.println("Errore, caso inatteso durante la registrazione");
        }
    }

    @Override
    public void reset() {
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
        setEyeIcon();
    }

    @FXML
    private void showDatePicker() {
        dataNascita.show();
    }

    @FXML
    public void switchToLogin() {
        Navigator.show("Login");
    }
}
