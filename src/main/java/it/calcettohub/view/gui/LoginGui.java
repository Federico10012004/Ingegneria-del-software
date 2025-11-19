package it.calcettohub.view.gui;

import it.calcettohub.bean.LoginBean;
import it.calcettohub.controller.LoginController;
import it.calcettohub.exceptions.EmailNotFoundException;
import it.calcettohub.exceptions.InvalidPasswordException;
import it.calcettohub.util.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

public class LoginGui extends BaseFormerGui {

    @FXML private Label errorLabel;
    @FXML private TextField emailField;
    @FXML private TextField textField;
    @FXML private PasswordField passwordField;
    @FXML private ImageView eyeImageView;

    private boolean isVisible = false;
    private final LoginController controller = new LoginController();

    @FXML
    public void initialize() {
        PasswordUtils.bindPasswordFields(passwordField, textField, isVisible);
        setEyeIcon();
        hideError(errorLabel);
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
        LoginBean bean = new LoginBean();
        bean.setRole(Navigator.getUserType());

        try {
            String email = emailField.getText().trim();
            bean.setEmail(email);

            String password = isVisible ? textField.getText().trim() : passwordField.getText().trim();
            bean.setPassword(password);

            controller.login(bean);

            Session session = SessionManager.getInstance().getCurrentSession();

            if (session == null) {
                errorLabel.setText("Errore: impossibile creare la sessione");
                showError(errorLabel);
                return;
            }

            switch (Navigator.getUserType()) {
                case PLAYER -> new HomePagePlayerGui().start();
                case FIELDMANAGER -> new HomePageFieldManagerGui().start();
                default -> throw new RuntimeException();
            }
        } catch (EmailNotFoundException | InvalidPasswordException | IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
            showError(errorLabel);
        } catch (RuntimeException e) {
            System.err.println("Errore inatteso nel caricamento della nuova schermata.");
            System.exit(1);
        }
    }

    @Override
    public void reset() {
        emailField.clear();
        passwordField.clear();
        textField.clear();
        hideError(errorLabel);

        isVisible = false;
        textField.setVisible(false);
        textField.setManaged(false);
        passwordField.setVisible(true);
        passwordField.setManaged(true);
        setEyeIcon();
    }

    @FXML
    public void goBack() {
        Navigator.show("Role Selection");
    }

    @FXML
    public void switchToRegister() {
        Navigator.setPreviousPage("Altro");

        switch (Navigator.getUserType()) {
            case PLAYER -> Navigator.show("Player Registration");
            case FIELDMANAGER -> Navigator.show("Field Manager Registration");
        }
    }
}