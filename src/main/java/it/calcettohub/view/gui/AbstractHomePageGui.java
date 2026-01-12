package it.calcettohub.view.gui;

import it.calcettohub.bean.AccountBean;
import it.calcettohub.controller.AccountController;
import it.calcettohub.controller.NotificationController;
import it.calcettohub.model.valueobject.Notification;
import it.calcettohub.utils.PasswordUtils;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.util.List;

public abstract class AbstractHomePageGui extends BaseFormerGui {
    @FXML protected ImageView eyeIcon;
    @FXML protected ImageView eyeConfirmIcon;
    @FXML protected ImageView fieldIcon;
    @FXML protected ImageView accountIcon;
    @FXML protected ImageView notificationIcon;
    @FXML protected ImageView backIcon;
    @FXML protected ImageView backIcon1;
    @FXML protected ImageView backIcon2;
    @FXML protected ImageView reservationIcon;
    @FXML protected Label errorLabel;
    @FXML protected Label errorLabel1;
    @FXML protected Label accountLabel;
    @FXML protected Label passwordLabel;
    @FXML protected Label fieldLabel;
    @FXML protected Label helloLabel;
    @FXML protected Label reservationLabel;
    @FXML protected Label notificationLabel;
    @FXML protected TextField nameField;
    @FXML protected TextField surnameField;
    @FXML protected TextField passwordTextField;
    @FXML protected TextField confirmPasswordTextField;
    @FXML protected PasswordField passwordField;
    @FXML protected PasswordField confirmPasswordField;
    @FXML protected Button confirmModifyButton;
    @FXML protected VBox accountPanel;
    @FXML protected VBox reservation;
    @FXML protected VBox changePasswordPanel;
    @FXML protected VBox notificationBox;
    @FXML protected VBox notificationPanel;
    @FXML protected HBox buttonBox;

    protected boolean isVisible = false;
    protected boolean isConfirmPasswordVisible = false;
    protected boolean suppressAccountChangeEvents = false;
    protected final AccountController accountController = new AccountController();
    protected final NotificationController notificationController = new NotificationController();

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
        setupResponsiveLabel(accountLabel, root, 35.0, FONT_STYLE_TAHOMA);
        setupResponsiveLabel(passwordLabel, root, 35.0, FONT_STYLE_TAHOMA);
        setupResponsiveLabel(notificationLabel, root, 35.0, FONT_STYLE_TAHOMA);
        setupResponsiveLabel(helloLabel, root, 45.0, FONT_STYLE_TAHOMA);

        setUpResponsiveNode(accountPanel, root, 0.40);
        setUpResponsiveNode(changePasswordPanel, root, 0.40);
        setUpResponsiveNode(notificationPanel, root, 0.40);

        setUpResponsiveIcon(backIcon, root, 0.02);
        setUpResponsiveIcon(backIcon1, root, 0.02);
        setUpResponsiveIcon(backIcon2, root, 0.02);
        setUpResponsiveIcon(accountIcon, root, 0.03);
        setUpResponsiveIcon(notificationIcon, root, 0.03);
    }

    protected abstract void populateFields();

    protected abstract void setupAccountChangeListeners();

    protected void updateConfirmButtonVisibility() {
        if (suppressAccountChangeEvents) return;
        setNodeVisibility(confirmModifyButton, hasAccountChanges());
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
    protected abstract void showNotifications();

    protected int loadNotifications() {
        notificationBox.getChildren().clear();

        List<Notification> notifications = notificationController.getNotifications();
        if (notifications.isEmpty()) {
            notificationBox.setAlignment(Pos.CENTER);

            Label empty = new Label("Nessuna nuova notifica");
            empty.getStyleClass().add("notification-item");
            empty.setWrapText(true);
            empty.setTextAlignment(TextAlignment.CENTER);
            empty.setAlignment(Pos.CENTER);
            empty.setMaxWidth(Double.MAX_VALUE);

            notificationBox.getChildren().add(empty);
            return 0;
        }

        notificationBox.setAlignment(Pos.TOP_LEFT);

        for (Notification not : notifications) {
            Label label = new Label(not.message());
            label.getStyleClass().add("notification-item");
            label.setWrapText(true);

            label.setMaxWidth(Double.MAX_VALUE);
            label.setMinHeight(Region.USE_PREF_SIZE);

            notificationBox.getChildren().add(label);
        }

        return notifications.size();
    }

    @FXML protected abstract void closeNotificationBox();

    protected abstract void resetHomeContent();

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

            accountController.updateUserPassword(bean);

            showInfo("Password modificata con successo.");

            clearPasswordPanel();
            setNodeVisibility(accountPanel, true);
            setNodeVisibility(changePasswordPanel, false);
        } catch (IllegalArgumentException e) {
            setErrorMessage(errorLabel1, e.getMessage());
            showErrorLabel(errorLabel1);
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
            accountController.updateUserData(bean);
            applyChanges(bean);

            showInfo("Modifiche applicate con successo.");
            setNodeVisibility(confirmModifyButton, false);
            setNodeVisibility(errorLabel, false);
            populateFields();
        } catch (IllegalArgumentException e) {
            setErrorMessage(errorLabel, e.getMessage());
            showErrorLabel(errorLabel);
        }
    }

    @Override
    public void reset() {
        clearPasswordPanel();

        setNodeVisibility(errorLabel, false);
        setNodeVisibility(confirmModifyButton, false);

        setNodeVisibility(accountPanel, false);
        setNodeVisibility(changePasswordPanel, false);
        setNodeVisibility(notificationPanel, false);

        resetHomeContent();

        suppressAccountChangeEvents = true;
        populateFields();
        suppressAccountChangeEvents = false;
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
    protected void backToHome() {
        if (hasAccountChanges()) {
            boolean confirm = showConfirmation(
                    "Sei sicuro di voler tornare indietro?",
                    "Le modifiche andranno perse.");

            if (!confirm) return;
        }

        setNodeVisibility(accountPanel, false);
        reservation.setVisible(true);
        buttonBox.setMouseTransparent(false);
        reset();
    }

    @FXML
    protected abstract void logout();

    @FXML
    protected void goToManageBookings() {
        switchTo("Manage Bookings");
    }
}
