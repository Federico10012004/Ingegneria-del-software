package it.uniroma2.dicii.CalcettoHub.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import it.uniroma2.dicii.CalcettoHub.core.Navigator;
import it.uniroma2.dicii.CalcettoHub.dao.LoginDao;
import it.uniroma2.dicii.CalcettoHub.util.PasswordUtils;

public class LoginController extends BaseFormerController {

    @FXML private Label errorLabel, emptyFieldError;
    @FXML private TextField emailField, textField;
    @FXML private PasswordField passwordField;
    @FXML private ImageView eyeImageView;

    private boolean isVisible = false;
    private final LoginDao loginDao = new LoginDao();

    @FXML
    public void initialize() {
        PasswordUtils.bindPasswordFields(passwordField, textField, isVisible);
        setEyeIcon();
        hideAllErrors(errorLabel, emptyFieldError);
        setupResponsiveLabel(30.0);
    }

    private void setEyeIcon() {
        PasswordUtils.updateEyeIcon(eyeImageView, isVisible);
    }

    @FXML
    private void togglePasswordVisibility() {
        isVisible = PasswordUtils.togglePasswordVisibility(passwordField, textField, isVisible);
        setEyeIcon();
    }

    @FXML
    private void handleLogin() {
        String email = emailField.getText().trim();
        String password = isVisible ? textField.getText().trim() : passwordField.getText().trim();

        int result = loginDao.checkCredentials(email, password);
        switch (result) {
            case 1 -> showError(errorLabel, emptyFieldError);
            case -1 -> showError(emptyFieldError, errorLabel);
            case 0 -> {
                hideAllErrors(errorLabel, emptyFieldError);
                System.out.println("✅ Login riuscito!");
            }
            default -> System.err.println("Errore, caso inatteso nel login");
        }
    }

    @Override
    public void reset() {
        emailField.clear();
        passwordField.clear();
        textField.clear();
        hideAllErrors(errorLabel, emptyFieldError);

        isVisible = false;
        textField.setVisible(false);
        textField.setManaged(false);
        passwordField.setVisible(true);
        passwordField.setManaged(true);
        setEyeIcon();
    }

    @FXML
    public void switchToHome() {
        Navigator.show("Welcome");
    }

    @FXML
    public void switchToRegister() {
        Navigator.show("Register");
    }
}
