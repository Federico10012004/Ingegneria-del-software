package it.calcettohub.view.gui;

import it.calcettohub.bean.LoginBean;
import it.calcettohub.controller.LoginController;
import it.calcettohub.exceptions.EmailNotFoundException;
import it.calcettohub.exceptions.InvalidPasswordException;
import it.calcettohub.exceptions.UnexpectedRoleException;
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
    private void initialize() {
        disableSessionCheck();

        PasswordUtils.bindPasswordFields(passwordField, textField, isVisible);
        setEyeIcon();
        setNodeVisibility(errorLabel, false);

        bindResponsiveLogo(logoGroup, 900.0);
        setupResponsiveLabel(sloganLabel, root, 60.0, FONT_STYLE_SLOGAN);
        setupResponsiveLabel(welcomeLabel, root, 30.0, FONT_STYLE_TAHOMA);
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
            validateField(()-> bean.setEmail(emailField.getText().trim()));
            validateField(()-> bean.setPassword(isVisible ? textField.getText().trim() : passwordField.getText().trim()));

            controller.login(bean);

            Session session = SessionManager.getInstance().getCurrentSession();

            if (session == null) {
                setErrorMessage(errorLabel, "Errore: impossibile creare la sessione");
                showError(errorLabel);
                return;
            }

            switch (Navigator.getUserType()) {
                case PLAYER -> switchTo("Home Player");
                case FIELDMANAGER -> switchTo("Home Field Manager");
                default -> throw new UnexpectedRoleException("Ruolo inatteso, errore nel caricamento della nuova scheramta.");
            }
        } catch (EmailNotFoundException | InvalidPasswordException | IllegalArgumentException e) {
            setErrorMessage(errorLabel, e.getMessage());
            showError(errorLabel);
        } catch (UnexpectedRoleException _) {
            System.exit(1);
        }
    }

    @Override
    public void reset() {
        emailField.clear();
        passwordField.clear();
        textField.clear();
        setNodeVisibility(errorLabel, false);

        isVisible = false;
        setNodeVisibility(textField, false);
        setNodeVisibility(passwordField, true);
        setEyeIcon();
    }

    @FXML
    private void goBack() {
        switchTo("Role Selection");
    }

    @FXML
    private void switchToRegister() {
        switchTo("Registration Gui");
    }
}