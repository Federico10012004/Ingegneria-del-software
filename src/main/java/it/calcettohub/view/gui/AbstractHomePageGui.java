package it.calcettohub.view.gui;

import it.calcettohub.bean.AccountBean;
import it.calcettohub.controller.AccountController;
import it.calcettohub.util.PasswordUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public abstract class AbstractHomePageGui extends BaseFormerGui {
    @FXML protected ImageView eyeIcon;
    @FXML protected ImageView eyeConfirmIcon;
    @FXML protected ImageView fieldIcon;
    @FXML protected ImageView accountIcon;
    @FXML protected ImageView backIcon;
    @FXML protected ImageView backIcon1;
    @FXML protected Label errorLabel;
    @FXML protected Label errorLabel1;
    @FXML protected Label accountLabel;
    @FXML protected Label passwordLabel;
    @FXML protected Label fieldLabel;
    @FXML protected Label helloLabel;
    @FXML protected TextField nameField;
    @FXML protected TextField surnameField;
    @FXML protected TextField passwordTextField;
    @FXML protected TextField confirmPasswordTextField;
    @FXML protected PasswordField passwordField;
    @FXML protected PasswordField confirmPasswordField;
    @FXML protected Button confirmModifyButton;
    @FXML protected VBox accountPanel;
    @FXML protected VBox changePasswordPanel;
    @FXML protected HBox buttonBox;

    protected boolean isVisible = false;
    protected boolean isConfirmPasswordVisible = false;
    protected final AccountController controller = new AccountController();

    @FXML
    protected void initializeCommon() {
        enableSessionCheck();

        PasswordUtils.bindPasswordFields(passwordField, passwordTextField, isVisible);
        PasswordUtils.bindPasswordFields(confirmPasswordField, confirmPasswordTextField, isConfirmPasswordVisible);

        setEyeIcon();
        setNodeVisibility(confirmModifyButton, false);
        setNodeVisibility(errorLabel, false);
        setNodeVisibility(errorLabel1, false);

        bindResponsiveLogo(logoGroup, 1000.0);
        setupResponsiveLabel(fieldLabel, root, 30.0, FONT_STYLE_TAHOMA);
        setupResponsiveLabel(accountLabel, root, 35.0, FONT_STYLE_TAHOMA);
        setupResponsiveLabel(passwordLabel, root, 35.0, FONT_STYLE_TAHOMA);
        setupResponsiveLabel(helloLabel, root, 45.0, FONT_STYLE_TAHOMA);

        setUpResponsiveNode(accountPanel, root, 0.40);
        setUpResponsiveNode(changePasswordPanel, root, 0.40);

        setUpResponsiveIcon(fieldIcon, root, 0.075);
        setUpResponsiveIcon(backIcon, root, 0.02);
        setUpResponsiveIcon(backIcon1, root, 0.02);
        setUpResponsiveIcon(accountIcon, root, 0.03);
    }

    protected abstract void populateFields();

    protected abstract void setupAccountChangeListeners();

    protected void updateConfirmButtonVisibility() {
        boolean changed = hasAccountChanges();
        setNodeVisibility(confirmModifyButton, changed);
    }

    protected abstract boolean hasAccountChanges();

    @FXML
    protected void togglePasswordVisibility(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        ImageView clickedEye = (ImageView) clickedButton.getGraphic();

        if (clickedEye == eyeIcon) {
            isVisible = PasswordUtils.togglePasswordVisibility(passwordField, passwordTextField, isVisible);
        } else if (clickedEye == eyeConfirmIcon) {
            isConfirmPasswordVisible = PasswordUtils.togglePasswordVisibility(confirmPasswordField, confirmPasswordTextField, isConfirmPasswordVisible);
        }
        setEyeIcon();
    }

    protected void setEyeIcon() {
        PasswordUtils.updateEyeIcon(eyeIcon, isVisible);
        PasswordUtils.updateEyeIcon(eyeConfirmIcon, isConfirmPasswordVisible);
    }

    @FXML
    protected abstract void showAccount();

    @FXML
    protected void showChangePassword() {
        setNodeVisibility(changePasswordPanel, true);
        setNodeVisibility(accountPanel, false);
    }

    @FXML
    protected void changePassword() {
        AccountBean bean = createAccountBean();

        try {
            validateField(()-> bean.setPassword(isVisible ? passwordTextField.getText().trim() : passwordField.getText().trim()));
            validateField(()-> bean.setConfirmPassword(isConfirmPasswordVisible ? confirmPasswordTextField.getText().trim() : confirmPasswordField.getText().trim()));

            controller.updateUserPassword(bean);

            showInfo("Password modificata con successo.");

            reset();
            setNodeVisibility(accountPanel, true);
            setNodeVisibility(changePasswordPanel, false);
        } catch (IllegalArgumentException e) {
            setErrorMessage(errorLabel1, e.getMessage());
            showError(errorLabel1);
        }
    }

    protected abstract AccountBean createAccountBean();

    protected abstract void fillAccountBean(AccountBean bean);

    protected abstract void applyChanges(AccountBean bean);

    @FXML
    protected void modifyAccount() {
        if (!hasAccountChanges()) {
            return;
        }

        AccountBean bean = createAccountBean();

        try {
            fillAccountBean(bean);
            controller.updateUserData(bean);
            applyChanges(bean);

            showInfo("Modifiche applicate con successo.");
            setNodeVisibility(confirmModifyButton, false);
            populateFields();
        } catch (IllegalArgumentException e) {
            setErrorMessage(errorLabel, e.getMessage());
            showError(errorLabel);
        }
    }

    @Override
    public void reset() {
        clearPasswordPanel();

        setNodeVisibility(errorLabel, false);
        setNodeVisibility(accountPanel, false);
        setNodeVisibility(changePasswordPanel, false);

        populateFields();
    }

    protected void clearPasswordPanel() {
        passwordField.clear();
        confirmPasswordField.clear();

        setNodeVisibility(errorLabel1, false);

        isVisible = false;
        setNodeVisibility(passwordTextField, false);
        setNodeVisibility(passwordField, true);

        isConfirmPasswordVisible = false;
        setNodeVisibility(confirmPasswordTextField, false);
        setNodeVisibility(confirmPasswordField, true);

        setEyeIcon();
    }

    @FXML
    protected void backToAccountPanel() {
        boolean confirm = showConfirmation(
                "Sei sicuro di voler tornare indietro?",
                "La password rimarrà invariata.");

        if (!confirm) return;

        clearPasswordPanel();
        showAccount();
    }

    @FXML
    protected abstract void backToHome();

    @FXML
    protected abstract void logout();
}
